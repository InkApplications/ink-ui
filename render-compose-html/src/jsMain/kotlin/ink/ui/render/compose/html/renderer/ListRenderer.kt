package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.Orientation
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.dom.Div

object ListRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is ElementList) return RenderResult.Skipped

        if (element.groupingStyle == GroupingStyle.Inline) {
            element.items.forEach {
                parent.render(it, parent)
            }
            return RenderResult.Rendered
        }

        val orientation = when (element.orientation) {
            Orientation.Horizontal -> "horizontal"
            Orientation.Vertical -> "vertical"
        }

        Div(
            attrs = {
                when (element.groupingStyle) {
                    GroupingStyle.Unified -> {
                        classes("unified-list", orientation)
                    }
                    GroupingStyle.Items -> {
                        classes("item-list", orientation)
                    }
                    GroupingStyle.Sections -> {
                        classes("section-list", orientation)
                    }
                    GroupingStyle.Inline -> {
                        throw IllegalStateException("Inline elements should be rendered without container.")
                    }
                }
                style {
                    justifyContent(when (element.positioning) {
                        Positioning.Start -> {
                            JustifyContent.Start
                        }
                        Positioning.Center -> {
                            JustifyContent.Center
                        }
                    })
                }
            }
        ) {
            element.items.forEach {
                when (element.groupingStyle) {
                    GroupingStyle.Unified -> parent.render(it, parent)
                    else -> Div {
                        parent.render(it, parent)
                    }
                }
            }
        }

        return RenderResult.Rendered
    }
}
