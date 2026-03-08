package ink.ui.structures.elements

/**
 * A clickable element in a list of related actions.
 */
data class MenuRowElement(
    /**
     * Primary Label displayed in the row.
     */
    val text: String,

    /**
     * Invoked when the row is clicked.
     */
    val onClick: (() -> Unit)? = null,

    /**
     * Optional element to display on the right side of the row.
     */
    val rightElement: UiElement? = null,
): UiElement.Interactive
