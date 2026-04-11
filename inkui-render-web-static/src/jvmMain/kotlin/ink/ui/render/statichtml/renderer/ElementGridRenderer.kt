package ink.ui.render.statichtml.renderer

import ink.ui.render.web.elements.gridTemplateColumns
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementGrid
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.TagConsumer
import kotlinx.html.div
import kotlinx.html.section

object ElementGridRenderer: ElementRenderer {
    override fun TagConsumer<*>.render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is ElementGrid) return RenderResult.Skipped

        section("fixed-grid") {
            attributes["style"] = "grid-template-columns: ${element.gridTemplateColumns};"

            element.items.forEach { item ->
                div {
                    val horizontalPosition = when (item.horizontalPositioning) {
                        Positioning.Start -> "start"
                        Positioning.Center -> "center"
                    }
                    val verticalPosition = when (item.verticalPositioning) {
                        Positioning.Start -> "start"
                        Positioning.Center -> "center"
                    }
                    attributes["style"] = "grid-column: span ${item.span}; align-items: $verticalPosition; justify-content: $horizontalPosition;"
                    renderWith(
                        element = item.body,
                        consumer = consumer,
                        renderer = parent,
                    )
                }
            }
        }

        return RenderResult.Rendered
    }
}
