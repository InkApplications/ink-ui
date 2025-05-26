package ink.ui.render.compose.html.renderer

import ink.ui.render.web.composePath
import ink.ui.render.web.elements.LinkButtonElement
import ink.ui.render.web.toCssClass
import org.jetbrains.compose.web.dom.*

val LinkButtonRenderer = renderer<LinkButtonElement> { element ->
    val leadingSymbol = element.leadingSymbol
    val trailingSymbol = element.trailingSymbol

    A(
        attrs = {
            classes("button", element.sentiment.toCssClass())
        },
        href = element.url
    ) {
        if (leadingSymbol != null) {
            Img(
                attrs = {
                    classes("icon", "svg-fill", element.sentiment.toCssClass())
                },
                src = leadingSymbol.composePath,
            )
        }
        Text(element.text)
        if (trailingSymbol != null) {
            Img(
                attrs = {
                    classes("icon", "svg-fill", element.sentiment.toCssClass())
                },
                src = trailingSymbol.composePath,
            )
        }
    }
}
