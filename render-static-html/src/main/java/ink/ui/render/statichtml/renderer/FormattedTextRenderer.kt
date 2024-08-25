package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.FormattedText
import kotlinx.html.TagConsumer
import kotlinx.html.*

val FormattedTextRenderer = renderer<FormattedText> { element ->
    p {
        render(consumer, element.spans)
    }
}

private fun Tag.render(
    consumer: TagConsumer<*>,
    spans: List<FormattedText.Span>,
) {
    spans.forEachIndexed { index, span ->
        when (span) {
            is FormattedText.Span.Text -> +span.text
            is FormattedText.Span.Emphasis -> consumer.em {
                render(consumer, span.inner)
            }
            is FormattedText.Span.Link -> consumer.a(
                href = span.url,
            ) {
                render(consumer, span.inner)
            }
            is FormattedText.Span.Strong -> consumer.strong {
                render(consumer, span.inner)
            }
        }
    }
}
