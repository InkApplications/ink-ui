package ink.ui.structures.elements

import ink.ui.structures.TextStyle

data class FormattedText(
    val spans: List<Span>,
    val paragraph: Boolean = true,
): UiElement.Static {
    constructor(
        paragraph: Boolean = true,
        builder: Builder.() -> Unit,
    ): this(
        spans = Builder().apply(builder).spans,
        paragraph = paragraph,
    )

    fun toPlainTextElement(
        style: TextStyle,
    ): TextElement {
        return TextElement(
            text = spans.joinToString("") { span ->
                when (span) {
                    is Span.Text -> span.text
                    is Span.Composite -> span.inner.joinToString("") { it.toPlainString() }
                    Span.Break -> "\n"
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
        data class Superscript(
            override val inner: List<Span>,
        ): Composite
        data class Subscript(
            override val inner: List<Span>,
        ): Composite
        data object Break: Span

        fun toPlainString(): String {
            return when (this) {
                is Text -> text
                is Composite -> inner.joinToString("") { it.toPlainString() }
                Break -> "\n"
            }
        }
    }

    data class Builder(
        var spans: MutableList<Span> = mutableListOf(),
    ) {
        fun strong(builder: Builder.() -> Unit) {
            spans.add(Span.Strong(Builder().apply(builder).spans))
        }
        fun strong(text: String) = strong { text(text) }

        fun emphasis(builder: Builder.() -> Unit) {
            spans.add(Span.Emphasis(Builder().apply(builder).spans))
        }
        fun emphasis(text: String) = emphasis { text(text) }

        fun code(group: Boolean = false, builder: Builder.() -> Unit) {
            spans.add(Span.Code(Builder().apply(builder).spans, group))
        }
        fun code(text: String, group: Boolean = false) = code(group) { text(text) }

        fun sup(builder: Builder.() -> Unit) {
            spans.add(Span.Superscript(Builder().apply(builder).spans))
        }
        fun sup(text: String) = sup { text(text) }

        fun sub(builder: Builder.() -> Unit) {
            spans.add(Span.Subscript(Builder().apply(builder).spans))
        }
        fun sub(text: String) = sub { text(text) }

        fun text(text: String) {
            spans.add(Span.Text(text))
        }
        fun link(url: String, builder: Builder.() -> Unit) {
            spans.add(Span.Link(Builder().apply(builder).spans, url))
        }
        fun space() {
            spans.add(Span.Text(" "))
        }
        fun br() {
            spans.add(Span.Break)
        }
    }
}
