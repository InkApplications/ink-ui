package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.checkBox
import ink.ui.structures.elements.spinner

val Forms = ElementList.build(groupingStyle = Items) {
    checkBox(
        checked = false,
        onClick = {},
    )
    checkBox(
        checked = true,
        onClick = {},
    )
    spinner(
        value = "1",
        onNextValue = {},
        onPreviousValue = {},
    )
    spinner(
        value = "0",
        onNextValue = {},
        onPreviousValue = {},
        hasNextValue = false,
        hasPreviousValue = false,
    )
}
