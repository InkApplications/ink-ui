package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.checkBox

val Forms = ElementList.build(groupingStyle = Items) {
    checkBox(
        checked = false,
        onClick = {},
    )
    checkBox(
        checked = true,
        onClick = {},
    )
}
