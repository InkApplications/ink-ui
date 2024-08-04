package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.UiElement

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
