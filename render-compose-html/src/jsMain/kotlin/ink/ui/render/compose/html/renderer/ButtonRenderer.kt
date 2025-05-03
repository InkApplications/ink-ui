package ink.ui.render.compose.html.renderer

import ink.ui.render.web.composePath
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.ButtonElement
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

val ButtonRenderer = renderer<ButtonElement> { element ->
    val leadingSymbol = element.leadingSymbol
    val trailingSymbol = element.trailingSymbol

    Button(
        attrs = {
            classes(element.sentiment.toCssClass())
            onClick {
                element.onClick()
            }
            onContextMenu {
                if (element.onContextClick != null) {
                    it.preventDefault()
                    element.onContextClick?.invoke()
                }
            }
        }
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
