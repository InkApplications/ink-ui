package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.DividerElement
import kotlinx.html.hr

val DividerRenderer = renderer<DividerElement> { element ->
    hr()
}
