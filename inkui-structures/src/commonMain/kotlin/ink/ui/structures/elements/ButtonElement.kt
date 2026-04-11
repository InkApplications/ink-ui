package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol

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
    val onClick: Action? = null,

    /**
     * Action to be invoked when the button has an alternate click.
     *
     * This may be a long-click or a right click depending on the platform.
     */
    val onContextClick: Action? = null,

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

fun ElementContainer.button(
    text: String,
    onClick: Action? = null,
    onContextClick: Action? = null,
    sentiment: Sentiment = Sentiment.Nominal,
    enabled: Boolean = true,
    latchOnPress: Boolean = false,
    leadingSymbol: Symbol? = null,
    trailingSymbol: Symbol? = null,
) {
    ButtonElement(
        text = text,
        onClick = onClick,
        onContextClick = onContextClick,
        sentiment = sentiment,
        enabled = enabled,
        latchOnPress = latchOnPress,
        leadingSymbol = leadingSymbol,
        trailingSymbol = trailingSymbol,
    ).apply(::append)
}
