package ink.ui.render.compose.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.UiElement

/**
 * Renders a UI element in compose.
 */
interface ElementRenderer {
    @Composable
    fun render(
        element: UiElement,
        theme: ComposeRenderTheme,
        parent: ElementRenderer
    ): RenderResult
}
