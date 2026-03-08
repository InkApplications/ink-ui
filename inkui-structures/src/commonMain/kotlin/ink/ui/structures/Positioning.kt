package ink.ui.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Describes the visual alignment of an element within its parent.
 */
@Serializable
enum class Positioning
{
    @SerialName("start")
    Start,
    @SerialName("center")
    Center
}
