package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching

/**
 * Renders a UI element in compose.
 */
interface ElementRenderer {
    @Composable
    fun render(
        element: UiElement,
        parent: ElementRenderer
    ): RenderResult
}

inline fun <reified T: UiElement> renderer(
    crossinline render: @Composable (element: T) -> Unit
): ElementRenderer = object: ElementRenderer {
    @Composable
    override fun render(
        element: UiElement,
        parent: ElementRenderer
    ): RenderResult {
        if (element !is T) return RenderResult.Skipped
        return renderCatching {
            render(element)
            RenderResult.Rendered
        }
    }
}
