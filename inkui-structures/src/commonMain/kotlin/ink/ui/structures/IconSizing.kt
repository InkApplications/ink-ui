package ink.ui.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Size of a signifier icon in the UI.
 */
@Serializable
enum class IconSizing
{
    @SerialName("small")
    Small,
    @SerialName("medium")
    Medium,
    @SerialName("large")
    Large,
}
