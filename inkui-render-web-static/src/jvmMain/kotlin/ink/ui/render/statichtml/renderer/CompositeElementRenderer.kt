package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.MissingRendererBehavior.Panic.MissingRendererException
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching
import kotlinx.html.*

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
    private val missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
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

        when (missingRendererBehavior) {
            is MissingRendererBehavior.Placeholder -> {
                missingRendererBehavior.log(element)
                div("error-box") {
                    +"{{ ${element::class.simpleName} }}"
                }
            }
            is MissingRendererBehavior.Ignore -> {
                missingRendererBehavior.log(element)
            }
            is MissingRendererBehavior.Panic -> throw MissingRendererException(element)
        }

        return RenderResult.Skipped
    }
}
