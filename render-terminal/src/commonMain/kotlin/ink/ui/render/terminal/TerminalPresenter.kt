package ink.ui.render.terminal

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import ink.ui.render.terminal.renderer.*
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import ink.ui.structures.render.RenderResult
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach

class TerminalPresenter(
    renderers: List<ElementRenderer> = emptyList(),
    private val renderScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    private val missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    val builtInRenderers: List<ElementRenderer> = listOf(
        ListRenderer,
        StackRenderer,
        TextRenderer,
        StatusRenderer,
    )
    val renderer = CompositeElementRenderer(renderers + builtInRenderers)
    private val renderQueue = Channel<UiLayout>()

    fun bind(): Job
    {
        return renderScope.launch {
            renderQueue.consumeEach { layout ->
                when (layout) {
                    is ScrollingListLayout -> layout.items.forEach { item ->
                        render(item)
                    }
                    else -> TODO("Layout not implemented")
                }
            }
        }
    }

    override fun presentLayout(layout: UiLayout)
    {
        renderScope.launch {
            renderQueue.send(layout)
        }
    }

    private suspend fun render(element: UiElement)
    {
        val result = renderer.render(element, renderer)

        when (result) {
            RenderResult.Skipped -> when (missingRendererBehavior) {
                is MissingRendererBehavior.Placeholder -> {
                    println(TextStyles.bold(TextColors.red("{{ ${element::class.simpleName} }}")))
                }
                is MissingRendererBehavior.Ignore -> {
                    missingRendererBehavior.log(element)
                }
                MissingRendererBehavior.Panic -> throw MissingRendererBehavior.Panic.MissingRendererException(element)
            }
            is RenderResult.Failed -> throw result.exception
            RenderResult.Rendered -> {}
        }
    }
}

fun TerminalPresenter.bindAndPresent(layout: UiLayout): Job
{
    return bind().also {
        presentLayout(layout)
    }
}
