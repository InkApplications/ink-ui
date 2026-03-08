package ink.ui.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Class of text, informing the size, weight and typeface.
 */
@Serializable
enum class TextStyle
{
    @SerialName("h1")
    H1,
    @SerialName("h2")
    H2,
    @SerialName("h3")
    H3,
    @SerialName("body")
    Body,
    @SerialName("caption")
    Caption,
}
