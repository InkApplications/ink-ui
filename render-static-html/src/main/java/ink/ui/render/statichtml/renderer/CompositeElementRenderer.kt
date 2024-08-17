package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.UiElement
import kotlinx.html.TagConsumer

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
): ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderWith(
                element = element,
                consumer = this@render,
                renderer = renderer,
                parent = this@CompositeElementRenderer,
            )
            if (result == RenderResult.Rendered) {
                return RenderResult.Rendered
            }
        }
        return RenderResult.NotRendered
    }
}
