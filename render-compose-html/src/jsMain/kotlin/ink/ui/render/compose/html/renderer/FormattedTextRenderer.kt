package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.FormattedText
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Code
import org.jetbrains.compose.web.dom.Em
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Sub
import org.jetbrains.compose.web.dom.Sup
import org.jetbrains.compose.web.dom.TagElement
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement

val FormattedTextRenderer = renderer<FormattedText> { element ->
     if (element.paragraph) {
         P {
             render(element.spans)
         }
     } else {
         render(element.spans)
     }
}

@Composable
private fun render(spans: List<FormattedText.Span>) {
    spans.forEachIndexed { index, span ->
        when (span) {
            FormattedText.Span.Break -> Br()
            is FormattedText.Span.Code -> Code(
                attrs = {
                    if (span.group) classes("grouped")
                }
            ) {
                if (span.group) {
                    span.inner.forEach { inner ->
                        Span {
                            render(listOf(inner))
                        }
                    }
                } else {
                    render(span.inner)
                }
            }
            is FormattedText.Span.Emphasis -> Em {
                render(span.inner)
            }
            is FormattedText.Span.Link -> A(href = span.url) {
                render(span.inner)
            }
            is FormattedText.Span.Strong -> TagElement<HTMLElement>(
                tagName = "strong",
                applyAttrs = null,
                content = {
                    render(span.inner)
                }
            )
            is FormattedText.Span.Subscript -> Sub {
                render(span.inner)
            }
            is FormattedText.Span.Superscript -> Sup {
                render(span.inner)
            }
            is FormattedText.Span.Text -> Text(span.text)
        }
    }
}
