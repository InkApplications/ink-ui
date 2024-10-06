package ink.ui.structures.layouts

import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.UiElement

/**
 * Scrolling list of UI elements.
 */
data class ScrollingListLayout(
    /**
     * Elements to be displayed in the scrolling section of the layout.
     */
    val items: List<UiElement>,

    /**
     * Signifiers for indicating how the elements are related to each other.
     */
    val groupingStyle: GroupingStyle = GroupingStyle.Items,
): UiLayout {
    constructor(
        vararg items: UiElement,
        groupingStyle: GroupingStyle = GroupingStyle.Items,
    ): this(
        items = items.toList(),
        groupingStyle = groupingStyle,
    )
}
