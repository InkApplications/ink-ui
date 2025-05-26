package ink.ui.render.terminal

import ink.ui.render.terminal.renderer.*
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
import kotlinx.coroutines.*

@Deprecated("Use TerminalPresenter instead")
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

    @Deprecated("Use TerminalPresenter instead")
    suspend fun render(layout: UiLayout)
    {
        when (layout) {
            is ScrollingListLayout -> layout.items.forEach { item ->
                renderer.render(item, renderer)
            }

            else -> TODO("Layout not implemented")
        }
    }

    @Deprecated("Use TerminalPresenter instead")
    fun renderAsync(layout: UiLayout): Job
    {
        return backgroundScope.launch {
            render(layout)
        }
    }
}
