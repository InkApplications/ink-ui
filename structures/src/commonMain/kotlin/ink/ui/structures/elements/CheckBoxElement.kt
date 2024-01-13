package ink.ui.structures.elements

/**
 * Checkbox form element.
 */
data class CheckBoxElement(
    /**
     * Whether the box currently is marked.
     */
    val checked: Boolean,

    /**
     * Invoked when the checkbox is clicked.
     *
     * This should update the [checked] state, the UI element will not
     * automatically update this.
     */
    val onClick: () -> Unit,
): UiElement.Interactive
