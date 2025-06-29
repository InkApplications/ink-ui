package ink.ui.structures.elements

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A bulleted or numbered list of text items.
 */
@Serializable
@SerialName("text-list")
data class TextListElement(
    val items: List<@Contextual FormattedText>,
    val ordered: Boolean = false,
): UiElement.Static {
    constructor(
        vararg items: FormattedText,
    ): this(
        items = items.toList(),
    )
}
