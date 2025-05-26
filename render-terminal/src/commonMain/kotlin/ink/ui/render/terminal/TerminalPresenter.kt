package ink.ui.render.terminal

import ink.ui.render.terminal.renderer.CompositeElementRenderer
import ink.ui.render.terminal.renderer.ElementRenderer
import ink.ui.render.terminal.renderer.ListRenderer
import ink.ui.render.terminal.renderer.StackRenderer
import ink.ui.render.terminal.renderer.StatusRenderer
import ink.ui.render.terminal.renderer.TextRenderer
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch

class TerminalPresenter(
    renderers: List<ElementRenderer> = emptyList(),
    private val renderScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
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
                        renderer.render(item, renderer)
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
}

fun TerminalPresenter.bindAndPresent(layout: UiLayout): Job
{
    return bind().also {
        presentLayout(layout)
    }
}
