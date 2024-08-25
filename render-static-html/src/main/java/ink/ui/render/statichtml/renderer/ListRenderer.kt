package ink.ui.render.statichtml.renderer

import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.*

object ListRenderer: ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is ElementList) return RenderResult.NotRendered

        div(
            classes = when (element.groupingStyle) {
                GroupingStyle.Unified -> "unified-list"
                GroupingStyle.Items -> "item-list"
                GroupingStyle.Sections -> "section-list"
            }
        ) {
            attributes["style"] = when (element.positioning) {
                Positioning.Start -> "justify-content: start"
                Positioning.Center -> "justify-content: center"
            }
            element.items.forEach { item ->
                div {
                    renderWith(item, consumer, parent)
                }
            }
        }

        return RenderResult.Rendered
    }
}


