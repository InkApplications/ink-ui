package example

import ink.ui.structures.elements.CheckBoxElement
import ink.ui.structures.elements.items

val Forms = items(
    CheckBoxElement(
        checked = false,
        onClick = {},
    ),
    CheckBoxElement(
        checked = true,
        onClick = {},
    ),
)
