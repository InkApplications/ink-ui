package ink.ui.render.compose.html.renderer

import ink.ui.structures.elements.StatusIndicatorElement
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

val StatusRenderer = renderer<StatusIndicatorElement> { element ->
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
}
