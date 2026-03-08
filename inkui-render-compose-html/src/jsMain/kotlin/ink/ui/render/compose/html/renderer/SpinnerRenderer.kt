package ink.ui.render.compose.html.renderer

import ink.ui.structures.elements.SpinnerElement
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.*

val SpinnerRenderer = renderer<SpinnerElement> { element ->
    Div(
        attrs = {
            classes("spinner")
        }
    ) {
        Button(
            attrs = {
                if (element.hasPreviousValue) {
                    onClick { element.onPreviousValue }
                } else {
                    disabled()
                }
            }
        ) {
            Text("<")
        }

        Span(
            attrs = {
                classes("spinner-value")
            }
        ) {
            Text(element.value)
        }

        Button(
            attrs = {
                if (element.hasNextValue) {
                    onClick { element.onNextValue }
                } else {
                    disabled()
                }
            }
        ) {
            Text(">")
        }
    }
}
