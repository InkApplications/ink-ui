package ink.ui.structures.elements

import ink.ui.structures.TextStyle

data class FormattedText(
    val spans: List<Span>
): UiElement.Static {
    constructor(builder: FormattedText.Builder.() -> Unit): this(
        spans = Builder().apply(builder).spans
    )

    fun toPlainTextElement(
        style: TextStyle,
    ): TextElement {
        return TextElement(
            text = spans.joinToString("") { span ->
                when (span) {
                    is Span.Text -> span.text
                    is Span.Composite -> span.inner.joinToString("") { it.toPlainString() }
                }
            },
            style = style,
        )
    }

    sealed interface Span {
        sealed interface Composite: Span {
            val inner: List<Span>
        }
        data class Text(
            val text: String,
        ): Span
        data class Strong(
            override val inner: List<Span>,
        ): Composite
        data class Emphasis(
            override val inner: List<Span>,
        ): Composite
        data class Code(
            override val inner: List<Span>,
            val group: Boolean = false,
        ): Composite
        data class Link(
            override val inner: List<Span>,
            val url: String,
        ): Composite

        fun toPlainString(): String {
            return when (this) {
                is Text -> text
                is Composite -> inner.joinToString("") { it.toPlainString() }
            }
        }
    }

    data class Builder(
        var spans: MutableList<Span> = mutableListOf(),
    ) {
        fun text(text: String) {
            spans.add(Span.Text(text))
        }
        fun strong(builder: Builder.() -> Unit) {
            spans.add(Span.Strong(Builder().apply(builder).spans))
        }
        fun emphasis(builder: Builder.() -> Unit) {
            spans.add(Span.Emphasis(Builder().apply(builder).spans))
        }
        fun link(url: String, builder: Builder.() -> Unit) {
            spans.add(Span.Link(Builder().apply(builder).spans, url))
        }
        fun code(group: Boolean = false, builder: Builder.() -> Unit) {
            spans.add(Span.Code(Builder().apply(builder).spans, group))
        }
        fun space() {
            spans.add(Span.Text(" "))
        }
    }
}
