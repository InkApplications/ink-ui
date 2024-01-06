package ink.ui.render.compose.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Sentiment

@Composable
internal fun StatusIndicator(
    text: String,
    sentiment: Sentiment,
    theme: ComposeRenderTheme = ComposeRenderTheme(),
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(
            modifier = Modifier.size(16.dp),
            onDraw = {
                drawCircle(
                    color = theme.colors.forSentiment(sentiment),
                    radius = 8.dp.toPx(),
                )
            }
        )
        Spacer(modifier = Modifier.padding(4.dp))
        BasicText(
            text = text,
            style = theme.typography.body.copy(
                color = theme.colors.foreground,
            )
        )
    }
}
