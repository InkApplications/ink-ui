package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import ink.ui.structures.elements.TextListElement.Builder
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

    data class Builder(
        val items: MutableList<FormattedText> = mutableListOf(),
        val ordered: Boolean = false,
    ) {
        fun formattedText(
            paragraph: Boolean = true,
            builder: FormattedText.Builder.() -> Unit,
        ) {
            items += FormattedText(
                spans = FormattedText.Builder().apply(builder).spans,
                paragraph = paragraph
            )
        }
    }

    companion object
    {
        fun build(
            ordered: Boolean = false,
            builder: Builder.() -> Unit
        ): TextListElement {
            return Builder(ordered = ordered)
                .apply(builder)
                .let {
                    TextListElement(
                        items = it.items,
                        ordered = it.ordered,
                    )
                }
        }
    }
}

fun ElementContainer.textList(
    ordered: Boolean = false,
    builder: Builder.() -> Unit
) {
    TextListElement.build(
        ordered = ordered,
        builder = builder,
    ).also(::append)
}
