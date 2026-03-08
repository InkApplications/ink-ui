package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.TextListElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.*

object TextListRenderer: ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is TextListElement) return RenderResult.Skipped

        if (element.ordered) {
            ol {
                element.items.map { it.copy(paragraph = false) }.forEach { item ->
                    li {
                        renderWith(
                            element = item,
                            consumer = consumer,
                            renderer = parent,
                        )
                    }
                }
            }
        } else {
            ul {
                element.items.map { it.copy(paragraph = false) }.forEach { item ->
                    li {
                        renderWith(
                            element = item,
                            consumer = consumer,
                            renderer = parent,
                        )
                    }
                }
            }
        }

        return RenderResult.Rendered
    }
}
