package ink.ui.structures.elements

import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol

/**
 * A vector icon for signifying a noun or action.
 */
data class IconElement(
    val symbol: Symbol,
    val sentiment: Sentiment = Sentiment.Nominal,
): UiElement.Static
