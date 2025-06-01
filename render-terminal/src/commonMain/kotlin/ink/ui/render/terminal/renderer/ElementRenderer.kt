package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching

interface ElementRenderer
{
    suspend fun render(element: UiElement, parent: ElementRenderer): RenderResult
}

data class RenderArgs<T: UiElement>(
    val element: T,
    val parent: ElementRenderer,
)

inline fun <reified T: UiElement> renderer(
    crossinline render: suspend RenderArgs<T>.() -> Unit,
): ElementRenderer = object: ElementRenderer {
    override suspend fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is T) return RenderResult.Skipped
        return renderCatching {
            render(RenderArgs(element, parent))
            RenderResult.Rendered
        }
    }
}
