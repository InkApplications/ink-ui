package ink.ui.structures.elements

import ink.ui.structures.Sentiment

/**
 * UI Element to indicate that work is being done.
 */
data class ThrobberElement(
    /**
     * Optional caption to indicate what work is being done.
     */
    val caption: String? = null,

    /**
     * Signifier of the state of the work being done.
     */
    val sentiment: Sentiment = Sentiment.Nominal,
): UiElement.Static
