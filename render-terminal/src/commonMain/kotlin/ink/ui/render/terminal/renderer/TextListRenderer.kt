package ink.ui.render.terminal.renderer

import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.Orientation
import ink.ui.structures.elements.TextListElement

val TextListRenderer = renderer<TextListElement> {
    element.items.forEachIndexed { index, item ->
        val prefix = if (element.ordered) " ${index + 1}. " else " - "
        print(prefix)
        parent.render(item, parent)
        spacing(
            orientation = Orientation.Vertical,
            groupingStyle = GroupingStyle.Items,
            isLast = index == element.items.lastIndex
        )
    }
}
