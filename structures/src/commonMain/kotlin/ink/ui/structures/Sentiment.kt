package ink.ui.structures

/**
 * Signifier for the purpose of a UI element.
 *
 * This is used in place of styling, by indicating the purpose or state
 * of a UI element.
 */
enum class Sentiment {
    /**
     * Default, normal, state of an element.
     */
    Nominal,

    /**
     * Indicate to the user that this is the preferred element.
     */
    Primary,

    /**
     * Indicate a successful or positive result for a UI element
     */
    Positive,

    /**
     * Indicate a failed or negative result for a UI element
     */
    Negative,

    /**
     * Indicate danger associated with a UI element
     */
    Caution,

    /**
     * Indicate inactivity or a lack of data for a UI element
     */
    Idle,
}
