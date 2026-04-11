package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.web.elements.gridTemplateColumns
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementGrid
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.gridColumn
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section

object ElementGridRenderer: ElementRenderer
{
    @Composable
    override fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is ElementGrid) return RenderResult.Skipped

        Section(
            attrs = {
                classes("fixed-grid")
                style {
                    gridTemplateColumns(element.gridTemplateColumns)
                }
            }
        ) {
            element.items.forEach {
                Div(
                    attrs = {
                        style {
                            gridColumn("span ${it.span}")
                            when (it.horizontalPositioning) {
                                Positioning.Start -> {
                                    justifyContent(JustifyContent.Start)
                                }

                                Positioning.Center -> {
                                    justifyContent(JustifyContent.Center)
                                }
                            }
                            when (it.verticalPositioning) {
                                Positioning.Start -> {
                                    alignItems(AlignItems.Start)
                                }

                                Positioning.Center -> {
                                    alignItems(AlignItems.Center)
                                }
                            }
                        }
                    }
                ) {
                    parent.render(it.body, parent)
                }
            }
        }

        return RenderResult.Rendered
    }

}
