package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.ElementList

val ListRenderer = renderer<ElementList> {
    element.items.forEach {
        parent.render(it, parent)
    }
}
