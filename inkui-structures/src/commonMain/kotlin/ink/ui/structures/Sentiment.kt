package ink.ui.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Signifier for the purpose of a UI element.
 *
 * This is used in place of styling, by indicating the purpose or state
 * of a UI element.
 */
@Serializable
enum class Sentiment
{
    /**
     * Default, normal, state of an element.
     */
    @SerialName("nominal")
    Nominal,

    /**
     * Indicate to the user that this is the preferred element.
     */
    @SerialName("primary")
    Primary,

    /**
     * Indicate a successful or positive result for a UI element
     */
    @SerialName("positive")
    Positive,

    /**
     * Indicate a failed or negative result for a UI element
     */
    @SerialName("negative")
    Negative,

    /**
     * Indicate danger associated with a UI element
     */
    @SerialName("caution")
    Caution,

    /**
     * Indicate inactivity or a lack of data for a UI element
     */
    @SerialName("idle")
    Idle,
}
