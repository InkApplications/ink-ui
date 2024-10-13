package ink.ui.structures.elements

/**
 * A bulleted or numbered list of text items.
 */
data class TextListElement(
    val items: List<FormattedText>,
    val ordered: Boolean = false,
): UiElement.Static {
    constructor(
        vararg items: FormattedText,
    ): this(
        items = items.toList(),
    )
}
