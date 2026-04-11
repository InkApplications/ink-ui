package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
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

fun ElementContainer.text(text: String, style: TextStyle = TextStyle.Body)
{
    TextElement(
        text = text,
        style = style,
    ).also(::append)
}

fun ElementContainer.h1(text: String) = text(text, TextStyle.H1)
fun ElementContainer.h2(text: String) = text(text, TextStyle.H2)
fun ElementContainer.h3(text: String) = text(text, TextStyle.H3)
fun ElementContainer.caption(text: String) = text(text, TextStyle.Caption)
