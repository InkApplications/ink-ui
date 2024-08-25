package ink.ui.render.compose.html.renderer

import ink.ui.render.web.svgSrc
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.IconElement
import org.jetbrains.compose.web.dom.Img

val IconRenderer = renderer<IconElement> { element ->
    Img(
        src = element.symbol.svgSrc,
        attrs = {
            classes("icon", "svg-fill", element.sentiment.toCssClass())
        }
    )
}
