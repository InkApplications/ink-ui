package ink.ui.render.compose.html.renderer

import ink.ui.render.web.composePath
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.IconElement
import org.jetbrains.compose.web.dom.Img

val IconRenderer = renderer<IconElement> { element ->
    Img(
        src = element.symbol.composePath,
        attrs = {
            classes("icon", "svg-fill", element.sentiment.toCssClass())
        }
    )
}
