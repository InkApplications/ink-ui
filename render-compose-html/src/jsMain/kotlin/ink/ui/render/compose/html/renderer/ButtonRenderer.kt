package ink.ui.render.compose.html.renderer

import ink.ui.render.web.composePath
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.ButtonElement
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

val ButtonRenderer = renderer<ButtonElement> { element ->
    Button(
        attrs = {
            classes(element.sentiment.toCssClass())
            onClick {
                element.onClick()
            }
        }
    ) {
        val leadingSymbol = element.leadingSymbol
        if (leadingSymbol != null) {
            Img(
                attrs = {
                    classes("icon", "svg-fill", element.sentiment.toCssClass())
                },
                src = leadingSymbol.composePath,
            )
        }
        Text(element.text)
    }
}
