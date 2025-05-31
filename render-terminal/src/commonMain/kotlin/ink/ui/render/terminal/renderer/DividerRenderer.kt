package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.DividerElement

val DividerRenderer = renderer<DividerElement> {
    println("-".repeat(80))
}
