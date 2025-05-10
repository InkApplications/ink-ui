package ink.ui.render.terminal

import ink.ui.render.terminal.renderer.CompositeElementRenderer
import ink.ui.render.terminal.renderer.ElementRenderer
import ink.ui.render.terminal.renderer.ListRenderer
import ink.ui.render.terminal.renderer.StackRenderer
import ink.ui.render.terminal.renderer.StatusRenderer
import ink.ui.render.terminal.renderer.TextRenderer
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TerminalRenderer(
    renderers: List<ElementRenderer> = emptyList(),
    private val backgroundScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {
    val builtInRenderers: List<ElementRenderer> = listOf(
        ListRenderer,
        StackRenderer,
        TextRenderer,
        StatusRenderer,
    )
    val renderer = CompositeElementRenderer(renderers + builtInRenderers)

    suspend fun render(layout: UiLayout)
    {
        when (layout) {
            is ScrollingListLayout -> layout.items.forEach { item ->
                renderer.render(item, renderer)
            }

            else -> TODO("Layout not implemented")
        }
    }

    fun renderAsync(layout: UiLayout): Job
    {
        return backgroundScope.launch {
            render(layout)
        }
    }
}
