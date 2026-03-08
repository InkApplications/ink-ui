package ink.ui.render.compose.html.renderer

import ink.ui.render.web.COMPOSE_RESOURCE_SVG_PATH
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.StatusIndicatorElement
import org.jetbrains.compose.web.dom.*

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
            src = "$COMPOSE_RESOURCE_SVG_PATH/blip.svg",
        )
        Span {
            Text(element.text)
        }
    }
}
