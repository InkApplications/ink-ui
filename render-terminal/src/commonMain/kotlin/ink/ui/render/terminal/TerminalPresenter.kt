package ink.ui.render.terminal

import ink.ui.render.terminal.renderer.*
import ink.ui.structures.elements.Orientation
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
        DividerRenderer,
        IconRenderer,
        FormattedTextRenderer,
        TextListRenderer,
    )
    private val renderQueue = Channel<UiLayout>()
    private val compositeRenderer = CompositeElementRenderer(renderers + builtInRenderers, missingRendererBehavior)

    fun bind(): Job
    {
        return renderScope.launch {
            renderQueue.consumeEach { layout ->
                when (layout) {
                    is ScrollingListLayout -> layout.items.forEachIndexed { index, item ->
                        compositeRenderer.render(item, compositeRenderer)
                        spacing(
                            orientation = Orientation.Vertical,
                            groupingStyle = layout.groupingStyle,
                            isLast = index == layout.items.lastIndex
                        )
                    }.also { print("\n") }
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
