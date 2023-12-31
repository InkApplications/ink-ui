package ink.ui.structures.elements

import ink.ui.structures.TextStyle

/**
 * A single contiguous block of text.
 */
data class TextElement(
    /**
     * Text to be displayed.
     */
    val text: String,

    /**
     * UI style to apply to the text.
     */
    val style: TextStyle = TextStyle.Body,
): UiElement.Static
