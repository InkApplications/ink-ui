package ink.ui.render.statichtml.renderer

import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.Orientation
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.*

object ListRenderer: ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is ElementList) return RenderResult.Skipped

        if (element.groupingStyle == GroupingStyle.Inline) {
            element.items.forEach {
                renderWith(it, this, parent)
            }

            return RenderResult.Rendered
        }

        val listStyle = when (element.groupingStyle) {
            GroupingStyle.Unified -> "unified-list"
            GroupingStyle.Items -> "item-list"
            GroupingStyle.Sections -> "section-list"
            GroupingStyle.Inline -> throw IllegalStateException("Inline elements should be rendered without container.")
        }

        val orientation = when (element.orientation) {
            Orientation.Horizontal -> "horizontal"
            Orientation.Vertical -> "vertical"
        }

        div(
            classes = setOf(listStyle, orientation).joinToString(" "),
        ) {
            attributes["style"] = when (element.positioning) {
                Positioning.Start -> "justify-content: start"
                Positioning.Center -> "justify-content: center"
            }
            element.items.forEach { item ->
                when (element.groupingStyle) {
                    GroupingStyle.Unified -> renderWith(item, consumer, parent)
                    else -> div {
                        renderWith(item, consumer, parent)
                    }
                }
            }
        }

        return RenderResult.Rendered
    }
}


