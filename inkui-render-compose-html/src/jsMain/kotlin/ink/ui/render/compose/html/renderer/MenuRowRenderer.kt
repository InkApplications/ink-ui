package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.MenuRowElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import org.jetbrains.compose.web.dom.*

object MenuRowRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is MenuRowElement) return RenderResult.Skipped
        val elementOnClick = element.onClick
        Div(
            attrs = {
                classes("menu-row")
                if (elementOnClick != null) onClick { elementOnClick() }
            }
        ) {
            Span(
                attrs = {
                    classes("menu-label")
                }
            ) {
                Text(element.text)
            }

            val rightElement = element.rightElement
            if (rightElement != null) {
                parent.render(rightElement, parent)
            }
        }

        return RenderResult.Rendered
    }
}
