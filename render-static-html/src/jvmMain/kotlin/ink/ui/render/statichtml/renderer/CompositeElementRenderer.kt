package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching
import kotlinx.html.TagConsumer

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
): ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderCatching {
                renderWith(
                    element = element,
                    consumer = this@render,
                    renderer = renderer,
                    parent = this@CompositeElementRenderer,
                )
            }
            if (result != RenderResult.Skipped) {
                return result
            }
        }
        return RenderResult.Skipped
    }
}
