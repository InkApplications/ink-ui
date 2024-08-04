package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.StatusIndicatorElement
import ink.ui.structures.elements.UiElement
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

object StatusRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is StatusIndicatorElement) return RenderResult.NotRendered

        Div(
            attrs = {
                classes("status-indicator")
            }
        ) {
            Img(
                attrs = {
                    classes("status-indicator-blip", "svg-fill", element.sentiment.toCssClass())
                },
                src = "$PATH/blip.svg",
            )
            Span {
                Text(element.text)
            }
        }

        return RenderResult.Rendered
    }
}
