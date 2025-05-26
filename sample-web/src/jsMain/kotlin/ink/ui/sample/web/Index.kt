package ink.ui.sample.web

import ink.ui.structures.GroupingStyle
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.inline
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout

fun Index(
    onTypographyClick: () -> Unit,
): UiLayout {
    return ScrollingListLayout(
        inline(
            TextElement("UI Reference", TextStyle.H1),
            TextElement("Visual references for various types of UI elements"),
            ButtonElement(
                text = "Typography",
                onClick = onTypographyClick,
            ),
        ),
        groupingStyle = GroupingStyle.Sections,
    )
}
