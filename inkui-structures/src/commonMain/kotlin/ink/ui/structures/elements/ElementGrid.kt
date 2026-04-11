package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import ink.ui.structures.GroupingStyle
import ink.ui.structures.MutableListElementContainer
import ink.ui.structures.Positioning
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("element-grid")
data class ElementGrid(
    /**
     * Number of columns in the grid.
     */
    val columns: Int,

    /**
     * Items to be displayed in the grid.
     */
    val items: List<GridItem>,
): UiElement.Static {
    /**
     * An individual item to display in the grid.
     */
    @Serializable
    data class GridItem(
        /**
         * Number of columns this item should take up.
         */
        val span: Int,

        /**
         * UI element to display in the grid area.
         */
        @Contextual
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

    class Builder(
        private val columns: Int,
    ) {
        private val items = mutableListOf<GridItem>()

        fun gridItem(
            span: Int = 1,
            horizontalPositioning: Positioning = Positioning.Start,
            verticalPositioning: Positioning = Positioning.Start,
            bodyBuilder: ElementContainer.() -> Unit,
        ) {
            val body = MutableListElementContainer()
                .apply(bodyBuilder)
                .build()
                .let {
                    ElementList(
                        items = it,
                        groupingStyle = GroupingStyle.Inline,
                    )
                }
            items.add(GridItem(span, body, horizontalPositioning, verticalPositioning))
        }

        fun build(): ElementGrid {
            return ElementGrid(columns, items)
        }
    }
}

fun ElementContainer.elementGrid(
    columns: Int,
    builder: ElementGrid.Builder.() -> Unit,
) {
    val elementBuilder = ElementGrid.Builder(columns)
    builder(elementBuilder)
    append(elementBuilder.build())
}
