package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.*
import ink.ui.render.web.toCssClass
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.*
import ink.ui.structures.render.RenderResult
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import kotlin.math.roundToInt

private const val Nbsp = "\u00A0"

object ActivityRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        when (element) {
            is ProgressElement -> ProgressBar(element)
            is ThrobberElement -> Throbber(element.caption, element.sentiment)
            else -> return RenderResult.Skipped
        }

        return RenderResult.Rendered
    }
}

@Composable
internal fun ProgressBar(
    element: ProgressElement,
) {
    when (element) {
        is ProgressElement.Determinate -> {
            val position = element.progress.times(10).roundToInt()
            BarText(
                barString = "${"=".repeat(position)}${Nbsp.repeat(10 - position)}",
                caption = element.caption,
                sentiment = element.sentiment,
            )
        }
        is ProgressElement.Indeterminate -> {
            val length = 10
            var position by remember { mutableStateOf(0) }
            LaunchedEffect(position) {
                delay(100)
                position = (position + 1) % length
            }
            BarText(
                barString = "${Nbsp.repeat(position)}-${Nbsp.repeat(length - position - 1)}",
                caption = element.caption,
                sentiment = element.sentiment,
            )
        }
    }
}

@Composable
private fun BarText(
    barString: String,
    caption: String?,
    sentiment: Sentiment,
) {
    Div(
        attrs = {
            classes("progress-bar")
        }
    ) {
        Span(
            attrs = {
                classes("glyph")
            }
        ) {
            Text("[")
            Span(
                attrs = {
                    classes(sentiment.toCssClass())
                }
            ) {
                Text(barString)
            }
            Text("]")
        }
        if (caption != null) {
            Span(
                attrs = {
                    classes("caption")
                }
            ) {
                Text(caption)
            }
        }
    }
}
@Composable
internal fun Throbber(
    caption: String? = null,
    sentiment: Sentiment = Sentiment.Nominal,
) {
    val chars = "⣷⣯⣟⡿⢿⣻⣽⣾"
    var index by remember {  mutableStateOf(0) }
    LaunchedEffect(index) {
        delay(100)
        index = (index + 1) % chars.length
    }

    Div(
        attrs = {
            style {
                display(DisplayStyle.LegacyInlineFlex)
                alignItems(AlignItems.Center)
            }
        }
    ) {
        Span(
            attrs = {
                classes("glyph", sentiment.toCssClass())
                style {
                    display(DisplayStyle.InlineBlock)
                    paddingBottom(0.5.cssRem)
                }
            }
        ) {
            Text(chars[index].toString())
        }
        if (caption != null) {
            Span(
                attrs = {
                    classes("caption")
                }
            ) {
                Text(caption)
            }
        }
    }
}
