package ink.ui.structures.elements

import ink.ui.structures.IconSizing
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A vector icon for signifying a noun or action.
 */
@Serializable
@SerialName("icon")
data class IconElement(
    val symbol: Symbol,
    val sentiment: Sentiment = Sentiment.Nominal,
    val sizing: IconSizing = IconSizing.Medium,
): UiElement.Static
