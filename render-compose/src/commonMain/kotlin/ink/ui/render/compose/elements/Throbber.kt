package ink.ui.render.compose.elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Sentiment
import kotlinx.coroutines.delay

@Composable
internal fun Throbber(
    caption: String? = null,
    sentiment: Sentiment = Sentiment.Nominal,
    theme: ComposeRenderTheme = ComposeRenderTheme(),
) {
    val chars = "⣷⣯⣟⡿⢿⣻⣽⣾"
    var index by remember {  mutableStateOf(0) }
    LaunchedEffect(index) {
        delay(100)
        index = (index + 1) % chars.length
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicText(
            text = chars[index].toString(),
            style = theme.typography.uiGlyph.copy(
                color = theme.colors.forSentiment(sentiment),
            ),
            modifier = Modifier.padding(
                top = max(theme.spacing.item - 3.dp, 0.dp),
                bottom = theme.spacing.item,
                start = theme.spacing.item,
                end = theme.spacing.item,
            ),
        )
        if (caption != null) {
            BasicText(
                text = caption,
                style = theme.typography.caption.copy(
                    color = theme.colors.foreground,
                ),
                modifier = Modifier.padding(
                    end = theme.spacing.item,
                    top = theme.spacing.item,
                    bottom = theme.spacing.item,
                ),
            )
        }
    }
}
