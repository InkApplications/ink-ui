package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.ElementList

val ListRenderer = renderer<ElementList> {
    element.items.forEachIndexed { index, item ->
        parent.render(item, parent)
        spacing(
            orientation = element.orientation,
            groupingStyle = element.groupingStyle,
            isLast = index == element.items.lastIndex,
        )
    }
}
