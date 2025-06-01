package ink.ui.render.compose.html.renderer

import ink.ui.structures.elements.DividerElement
import org.jetbrains.compose.web.dom.Hr

val DividerRenderer = renderer<DividerElement> { element ->
    Hr()
}
