package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.FormattedText
import kotlinx.html.TagConsumer
import kotlinx.html.*

val FormattedTextRenderer = renderer<FormattedText> { element ->
    if (element.paragraph) {
        p {
            render(consumer, element.spans)
        }
    } else {
        render(this, element.spans)
    }
}

private fun render(
    consumer: TagConsumer<*>,
    spans: List<FormattedText.Span>,
) {
    spans.forEachIndexed { index, span ->
        when (span) {
            is FormattedText.Span.Text -> consumer.onTagContent(span.text)
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
            is FormattedText.Span.Code -> consumer.code("grouped".takeIf { span.group }) {
                span.inner.forEach { inner ->
                    if (span.group) {
                        span {
                            render(consumer, listOf(inner))
                        }
                    } else {
                        render(consumer, span.inner)
                    }
                }
            }
            FormattedText.Span.Break -> consumer.br()
        }
    }
}
