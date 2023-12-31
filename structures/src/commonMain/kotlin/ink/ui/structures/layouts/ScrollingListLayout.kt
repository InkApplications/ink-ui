package ink.ui.structures.layouts

import ink.ui.structures.elements.UiElement

/**
 * Scrolling list of UI elements.
 */
data class ScrollingListLayout(
    /**
     * Elements to be displayed in the scrolling section of the layout.
     */
    val items: List<UiElement>,
): UiLayout
