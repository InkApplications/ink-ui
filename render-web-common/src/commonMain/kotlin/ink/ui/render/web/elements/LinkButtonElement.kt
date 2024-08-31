package ink.ui.render.web.elements

import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.UiElement

/**
 * A clickable navigation button not a part of a block of text.
 */
data class LinkButtonElement(
    /**
     * Text to display in the button element
     */
    val text: String,

    /**
     * Action to be invoked when the button is clicked.
     */
    val url: String,

    /**
     * Signifier of the button's action or current state.
     *
     * This changes the style of the button to indicate its purpose or state.
     */
    val sentiment: Sentiment = Sentiment.Nominal,

    /**
     * Flag to enable/disable the button.
     */
    val enabled: Boolean = true,

    /**
     * Whether to disable the button temporarily after pressing.
     */
    val latchOnPress: Boolean = false,

    /**
     * Symbol displayed at the start of the button.
     */
    val leadingSymbol: Symbol? = null,

    /**
     * Symbol displayed at the end of the button.
     */
    val trailingSymbol: Symbol? = null,
): UiElement.Interactive
