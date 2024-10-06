package ink.ui.structures.layouts

import ink.ui.structures.Positioning
import ink.ui.structures.elements.UiElement

/**
 * Simple Grid of UI elements.
 */
data class FixedGridLayout(
    /**
     * Number of columns in the grid.
     */
    val columns: Int,

    /**
     * Items to be displayed in the grid.
     */
    val items: List<GridItem>,
): UiLayout {
    constructor(
        vararg items: GridItem,
        columns: Int,
    ): this(
        columns = columns,
        items = items.toList(),
    )

    /**
     * An individual item to display in the grid.
     */
    data class GridItem(
        /**
         * Number of columns this item should take up.
         */
        val span: Int,

        /**
         * UI element to display in the grid area.
         */
        val body: UiElement,

        /**
         * Position of the element horizontally within its grid square.
         */
        val horizontalPositioning: Positioning = Positioning.Start,

        /**
         * Position of the element vertically within its grid square.
         */
        val verticalPositioning: Positioning = Positioning.Start,
    )
}
