package ink.ui.render.compose.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.render.compose.theme.defaultTheme
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.ProgressElement
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
internal fun ProgressBar(
    element: ProgressElement,
    theme: ComposeRenderTheme = defaultTheme(),
) {
    when (element) {
        is ProgressElement.Determinate -> {
            val position = element.progress.times(10).roundToInt()
            BarText(
                barString = "${"=".repeat(position)}${" ".repeat(10 - position)}",
                caption = element.caption,
                sentiment = element.sentiment,
                theme = theme,
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
                barString = "${" ".repeat(position)}-${" ".repeat(length - position - 1)}",
                caption = element.caption,
                sentiment = element.sentiment,
                theme = theme,
            )
        }
    }
}

@Composable
private fun BarText(
    barString: String,
    caption: String?,
    sentiment: Sentiment,
    theme: ComposeRenderTheme,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
    ) {
        Row(
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            BasicText(
                text = "[",
                style = theme.typography.uiGlyph.copy(
                    color = theme.colors.foreground,
                ),
                modifier = Modifier.padding(
                    top = theme.spacing.item,
                    bottom = theme.spacing.item,
                    start = theme.spacing.item,
                ),
            )
            BasicText(
                text = barString,
                style = theme.typography.uiGlyph.copy(
                    color = theme.colors.forSentiment(sentiment),
                ),
                modifier = Modifier.padding(
                    vertical = theme.spacing.item
                ),
            )
            BasicText(
                text = "]",
                style = theme.typography.uiGlyph.copy(
                    color = theme.colors.foreground,
                ),
                modifier = Modifier.padding(
                    top = theme.spacing.item,
                    bottom = theme.spacing.item,
                    end = theme.spacing.item,
                ),
            )
        }
        if (caption != null) {
            BasicText(
                text = caption,
                style = theme.typography.caption.copy(
                    color = theme.colors.foreground,
                ),
                modifier = Modifier.padding(
                    start = theme.spacing.item,
                    end = theme.spacing.item,
                    bottom = theme.spacing.item,
                ),
            )
        }
    }
}
