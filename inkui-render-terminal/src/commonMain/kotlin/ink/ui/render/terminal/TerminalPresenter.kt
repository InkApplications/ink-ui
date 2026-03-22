package ink.ui.render.terminal

import ink.ui.render.terminal.renderer.*
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.trySendBlocking

class TerminalPresenter(
    renderers: List<ElementRenderer> = emptyList(),
    private val renderScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    val builtInRenderers: List<ElementRenderer> = listOf(
        ListRenderer,
        StackRenderer,
        TextRenderer,
        StatusRenderer,
        DividerRenderer,
        IconRenderer,
        FormattedTextRenderer,
        TextListRenderer,
        ProgressRenderer,
        BreadCrumbRenderer,
        ButtonRenderer,
    )
    private val renderQueue = Channel<UiElement>()
    private val compositeRenderer = CompositeElementRenderer(renderers + builtInRenderers, missingRendererBehavior)

    fun bind(): Job
    {
        return renderScope.launch {
            renderQueue.consumeEach { item ->
                compositeRenderer.render(item, compositeRenderer)
                println()
            }
        }
    }

    override fun presentElement(element: UiElement)
    {
        renderQueue.trySendBlocking(element)
    }
}

fun TerminalPresenter.bindAndPresent(element: UiElement): Job
{
    return bind().also {
        presentElement(element)
    }
}
