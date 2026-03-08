package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.*

interface ElementRenderer {
    fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult
}

inline fun <reified T: UiElement> renderer(crossinline render: TagConsumer<*>.(T) -> Unit) = object: ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element is T) {
            render(element)
            return RenderResult.Rendered
        }
        return RenderResult.Skipped
    }
}

fun renderWith(
    element: UiElement,
    consumer: TagConsumer<*>,
    renderer: ElementRenderer,
    parent: ElementRenderer = renderer,
): RenderResult {
    return with(renderer) {
        with(consumer) {
            render(element, parent)
        }
    }
}
