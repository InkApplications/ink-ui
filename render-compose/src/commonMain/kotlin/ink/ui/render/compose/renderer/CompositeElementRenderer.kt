package ink.ui.render.compose.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.UiElement

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
): ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            if (renderer.render(element, theme, this) == RenderResult.Rendered) {
                return RenderResult.Rendered
            }
        }
        return RenderResult.NotRendered
    }
}
