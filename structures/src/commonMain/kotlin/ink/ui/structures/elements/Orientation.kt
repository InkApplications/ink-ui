package ink.ui.structures.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class Orientation
{
    @SerialName("horizontal")
    Horizontal,
    @SerialName("vertical")
    Vertical
}
