package ink.ui.render.compose.html.renderer

import ink.ui.structures.elements.EmptyElement
import org.jetbrains.compose.web.dom.Span

val EmptyRenderer = renderer<EmptyElement> { element ->
    Span {}
}
