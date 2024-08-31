package ink.ui.render.statichtml.renderer

import ink.ui.structures.TextStyle
import ink.ui.structures.elements.TextElement
import kotlinx.html.*

internal val TextRenderer = renderer<TextElement> { element ->
    when (element.style) {
        TextStyle.H1 -> h1 { +element.text }
        TextStyle.H2 -> h2 { +element.text }
        TextStyle.H3 -> h3 { +element.text }
        TextStyle.Body -> p { +element.text }
        TextStyle.Caption -> p("caption") { +element.text }
    }
}
