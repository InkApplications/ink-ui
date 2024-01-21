package ink.ui.structures.elements

/**
 * An element that can be cycled through values for.
 */
data class SpinnerElement(
    /**
     * The current selected value.
     */
    val value: String,

    /**
     * Change the current value to its next appropriate value.
     */
    val onNextValue: () -> Unit,

    /**
     * Change the current value to its previous value.
     */
    val onPreviousValue: () -> Unit,

    /**
     * Whether there is a previous value to cycle to.
     */
    val hasPreviousValue: Boolean = true,

    /**
     * Whether there is a next value to cycle to.
     */
    val hasNextValue: Boolean = true,
): UiElement.Interactive
