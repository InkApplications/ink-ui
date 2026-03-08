package ink.ui.render.web

import ink.ui.structures.layouts.FixedGridLayout

val FixedGridLayout.gridTemplateColumns: String get() {
    return (0 until columns).joinToString(" ") { "auto" }
}
