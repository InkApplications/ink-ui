package ink.ui.structures.elements

import ink.ui.structures.Positioning

/**
 * UI element that composites multiple elements together.
 */
data class ElementList(
    /**
     * Elements to be displayed in order.
     */
    val items: List<UiElement>,

    /**
     * Positioning of each element within its own bounding box in the list.
     */
    val positioning: Positioning = Positioning.Start,
): UiElement.Static
