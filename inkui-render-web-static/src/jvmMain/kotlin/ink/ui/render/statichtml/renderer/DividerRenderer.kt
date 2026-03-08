package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.DividerElement
import kotlinx.html.*

val DividerRenderer = renderer<DividerElement> { element ->
    hr()
}
