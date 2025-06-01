package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyle
import com.github.ajalt.mordant.rendering.TextStyles
import ink.ui.structures.elements.FormattedText

val FormattedTextRenderer = renderer<FormattedText> {
    element.spans.forEach { span -> print(getSpanOutput(span)) }
}

private fun getSpanOutput(span: FormattedText.Span): String
{
    return when (span) {
        FormattedText.Span.Break -> "\n"
        is FormattedText.Span.Code -> TextColors.cyan(getSpanOutput(span.inner, separate = span.group))
        is FormattedText.Span.Emphasis -> TextStyles.italic(getSpanOutput(span.inner))
        is FormattedText.Span.Link -> {
            val link = TextStyle(
                color = TextColors.blue.color,
                hyperlink = span.url,
            )
            "[${getSpanOutput(span.inner)}](${link(span.url)})"
        }
        is FormattedText.Span.Strong -> TextStyles.bold(getSpanOutput(span.inner))
        is FormattedText.Span.Subscript -> "~[${getSpanOutput(span.inner)}]"
        is FormattedText.Span.Superscript -> "^[${getSpanOutput(span.inner)}]"
        is FormattedText.Span.Text -> span.text
    }
}

private fun getSpanOutput(spans: List<FormattedText.Span>, separate: Boolean = false): String
{
    val inner = spans.joinToString(
        separator = if (separate) " " else ""
    ) { getSpanOutput(it) }
    val prefix = if (separate) "[" else ""
    val suffix = if (separate) "]" else ""

    return "$prefix$inner$suffix"
}
