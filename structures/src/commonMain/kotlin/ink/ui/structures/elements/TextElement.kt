package ink.ui.structures.elements

import ink.ui.structures.TextStyle
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A single contiguous block of text.
 */
@Serializable
@SerialName("text")
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
