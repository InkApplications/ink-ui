package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.dom.Div

object ListRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is ElementList) return RenderResult.Skipped

        Div(
            attrs = {
                when (element.groupingStyle) {
                    GroupingStyle.Unified -> {
                        classes("unified-list")
                    }
                    GroupingStyle.Items -> {
                        classes("item-list")
                    }
                    GroupingStyle.Sections -> {
                        classes("section-list")
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
                Div {
                    parent.render(it, parent)
                }
            }
        }

        return RenderResult.Rendered
    }
}
