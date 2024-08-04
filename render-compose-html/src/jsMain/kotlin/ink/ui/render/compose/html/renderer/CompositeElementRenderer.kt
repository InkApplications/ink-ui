package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.UiElement

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
): ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            if (renderer.render(element, this) == RenderResult.Rendered) {
                return RenderResult.Rendered
            }
        }
        return RenderResult.NotRendered
    }
}
