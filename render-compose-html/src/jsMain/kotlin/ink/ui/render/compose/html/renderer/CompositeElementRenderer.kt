package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
): ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderCatching { renderer.render(element, this) }

            if (result != RenderResult.Skipped) {
                return result
            }
        }
        return RenderResult.Skipped
    }
}
