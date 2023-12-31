package ink.ui.structures.elements

import ink.ui.structures.Sentiment

/**
 * A clickable button not a part of a block of text.
 */
data class ButtonElement(
    /**
     * Text to display in the button element
     */
    val text: String,

    /**
     * Action to be invoked when the button is clicked.
     */
    val onClick: () -> Unit,

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
): UiElement.Interactive
