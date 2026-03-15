package ink.ui.render.compose.elements

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.render.compose.theme.boxShadow
import ink.ui.render.compose.theme.defaultTheme
import ink.ui.structures.Sentiment

@Composable
internal fun StatusIndicator(
    text: String,
    sentiment: Sentiment,
    theme: ComposeRenderTheme = defaultTheme(),
) {
    val shape = RoundedCornerShape(16.dp)
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .boxShadow(
                color = theme.colors.heavyShadow,
                blur = 4.dp,
                cornerRadius = 16.dp,
            )
            .border(theme.sizing.borders, theme.colors.heavyHighlight, shape)
            .clip(shape)
            .background(theme.colors.surface)
            .padding(start = 4.dp, top = 4.dp, bottom = 4.dp, end = 8.dp),
    ) {
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
