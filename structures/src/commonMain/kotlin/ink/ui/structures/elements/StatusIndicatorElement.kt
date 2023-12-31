package ink.ui.structures.elements

import ink.ui.structures.Sentiment

/**
 * UI Element to indicate the current state of a described label.
 */
data class StatusIndicatorElement(
    /**
     * Text label, this is the item being described.
     */
    val text: String,

    /**
     * Sentiment to indicate for this item.
     */
    val sentiment: Sentiment,
): UiElement.Static
