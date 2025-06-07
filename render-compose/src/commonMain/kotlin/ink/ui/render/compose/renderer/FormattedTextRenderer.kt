package ink.ui.render.compose.renderer

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.FormattedText
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

@OptIn(ExperimentalTextApi::class)
class FormattedTextRenderer(
    private val onClickUrl: (String) -> Unit = {},
): ElementRenderer {
    @Composable
    override fun render(
        element: UiElement,
        theme: ComposeRenderTheme,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is FormattedText) {
            return RenderResult.Skipped
        }

        val annotated = buildAnnotatedString {
            appendSpans(theme, element.spans)
        }
        ClickableText(
            text = annotated,
            onClick = { offset ->
                val url = annotated.getUrlAnnotations(start = offset, end = offset)
                    .first().item.url

                onClickUrl(url)
            }
        )

        return RenderResult.Rendered
    }

    private fun AnnotatedString.Builder.appendSpans(
        theme: ComposeRenderTheme,
        spans: List<FormattedText.Span>,
    ) {
        spans.forEach { span ->
            when (span) {
                FormattedText.Span.Break -> append("\n")
                is FormattedText.Span.Code -> withStyle(
                    style = SpanStyle(
                        color = theme.colors.primary,
                    )
                ) {
                    appendSpans(theme, span.inner)
                }
                is FormattedText.Span.Emphasis -> withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Companion.Italic,
                    )
                ) {
                    appendSpans(theme, span.inner)
                }
                is FormattedText.Span.Link -> withStyle(
                    style = SpanStyle(
                        color = theme.colors.primary,
                        textDecoration = TextDecoration.Underline,
                    )
                ) {
                    withAnnotation(
                        urlAnnotation = UrlAnnotation(span.url)
                    ) {
                        appendSpans(theme, span.inner)
                    }
                }
                is FormattedText.Span.Strong -> withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Companion.Bold,
                    )
                ) {
                    appendSpans(theme, span.inner)
                }
                is FormattedText.Span.Subscript -> withStyle(
                    style = SpanStyle(
                        baselineShift = BaselineShift.Subscript,
                    )
                ) {
                        appendSpans(theme, span.inner)
                }
                is FormattedText.Span.Superscript -> withStyle(
                    style = SpanStyle(
                        baselineShift = BaselineShift.Superscript,
                    )
                ) {
                        appendSpans(theme, span.inner)
                }
                is FormattedText.Span.Text -> append(span.text)
            }
        }
    }
}
