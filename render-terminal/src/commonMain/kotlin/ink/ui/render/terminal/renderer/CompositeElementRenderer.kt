package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching

class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
): ElementRenderer {
    override suspend fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderCatching { renderer.render(element, this) }

            if (result != RenderResult.Skipped) {
                return result
            }
        }
        return RenderResult.Skipped
    }
}
