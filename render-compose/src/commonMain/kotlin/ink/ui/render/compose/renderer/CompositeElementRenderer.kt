package ink.ui.render.compose.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
): ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderCatching { renderer.render(element, theme, this) }

            if (result != RenderResult.Skipped) {
                return result
            }
        }
        return RenderResult.Skipped
    }
}
