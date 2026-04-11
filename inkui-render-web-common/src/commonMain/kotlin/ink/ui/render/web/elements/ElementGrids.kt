package ink.ui.render.web.elements

import ink.ui.structures.elements.ElementGrid

val ElementGrid.gridTemplateColumns: String get() {
    return (0 until columns).joinToString(" ") { "auto" }
}
