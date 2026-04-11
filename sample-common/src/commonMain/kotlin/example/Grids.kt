package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.Positioning
import ink.ui.structures.Symbol
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.elementGrid
import ink.ui.structures.elements.icon
import ink.ui.structures.elements.text

val Grids = ElementList.build(groupingStyle = Items) {
    elementGrid(columns = 2) {
        gridItem {
            text("Two of them")
        }
        gridItem {
            text("Two of them")
        }
    }
    elementGrid(columns = 3) {
        gridItem(
            horizontalPositioning = Positioning.Center,
            verticalPositioning = Positioning.Center,
        ) {
            icon(Symbol.Group)
        }
        gridItem(
            horizontalPositioning = Positioning.Center,
            verticalPositioning = Positioning.Center,
        ) {
            icon(Symbol.Group)
        }
        gridItem(
            horizontalPositioning = Positioning.Center,
            verticalPositioning = Positioning.Center,
        ) {
            icon(Symbol.Group)
        }
    }
}
