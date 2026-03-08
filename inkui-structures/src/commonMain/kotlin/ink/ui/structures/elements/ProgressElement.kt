package ink.ui.structures.elements

import ink.ui.structures.Sentiment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * UI Element to indicate linear progress of work being done.
 */
@Serializable
sealed interface ProgressElement: UiElement.Static
{
    /**
     * Optional caption to indicate what work is being done.
     */
    val caption: String?

    /**
     * Signifier of the state of the work being done.
     */
    val sentiment: Sentiment

    /**
     * UI element for a progress bar that is currently indeterminate.
     *
     * This differs from a [ThrobberElement] in that it indicates that the work
     * will follow a linear progression, but the amount of work is currently
     * unknown.
     */
    @Serializable
    @SerialName("indeterminate")
    data class Indeterminate(
        override val caption: String? = null,
        override val sentiment: Sentiment = Sentiment.Nominal,
    ): ProgressElement

    /**
     * UI Element for a progress bar where the progress is known.
     */
    @Serializable
    @SerialName("determinate")
    data class Determinate(
        /**
         * Fractional percentage to indicate the current progress of work.
         */
        val progress: Float,
        override val caption: String? = null,
        override val sentiment: Sentiment = Sentiment.Nominal,
    ): ProgressElement
}
