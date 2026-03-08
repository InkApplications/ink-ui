package ink.ui.structures.elements

import ink.ui.structures.Sentiment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * UI Element to indicate the current state of a described label.
 */
@Serializable
@SerialName("status-indicator")
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
