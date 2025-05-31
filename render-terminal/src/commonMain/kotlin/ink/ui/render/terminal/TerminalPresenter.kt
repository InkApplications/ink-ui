package ink.ui.render.terminal

import ink.ui.render.terminal.renderer.*
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
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
    )
    private val renderQueue = Channel<UiLayout>()
    private val compositeRenderer = CompositeElementRenderer(renderers + builtInRenderers, missingRendererBehavior)

    fun bind(): Job
    {
        return renderScope.launch {
            renderQueue.consumeEach { layout ->
                when (layout) {
                    is ScrollingListLayout -> layout.items.forEach { item ->
                        compositeRenderer.render(item, compositeRenderer)
                    }
                    else -> TODO("Layout not implemented")
                }
            }
        }
    }

    override fun presentLayout(layout: UiLayout)
    {
        renderQueue.trySendBlocking(layout)
    }
}

fun TerminalPresenter.bindAndPresent(layout: UiLayout): Job
{
    return bind().also {
        presentLayout(layout)
    }
}
