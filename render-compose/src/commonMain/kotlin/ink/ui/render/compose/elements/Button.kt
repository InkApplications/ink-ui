package ink.ui.render.compose.elements

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Sentiment
import kotlinx.coroutines.delay

@Composable
internal fun Button(
    text: String,
    sentiment: Sentiment = Sentiment.Nominal,
    latching: Boolean = false,
    theme: ComposeRenderTheme = ComposeRenderTheme(),
    onClick: () -> Unit,
) {
    var latched by remember(text, sentiment) { mutableStateOf(false) }
    val borderColor = if (latched) theme.colors.inactive else theme.colors.forSentiment(sentiment)
    val latchingClick = {
        if (latching) {
            latched = true
        }
        onClick()
    }
    Box(
        modifier = Modifier
            .semantics { role = Role.Button }
            .border(theme.sizing.borders, borderColor, RoundedCornerShape(theme.sizing.corners))
            .let { if (latched) it else it.clickable(onClick = latchingClick) }
            .padding(theme.spacing.clickSafety)
    ) {
        if (latched) {
            LaunchedEffect(latched) {
                delay(10_000)
                latched = false
            }
            BasicText(
                text = "-".repeat(text.length),
                style = theme.typography.body.copy(
                    color = theme.colors.foreground,
                )
            )

        } else {
            BasicText(
                text = text,
                style = theme.typography.body.copy(
                    color = theme.colors.foreground,
                )
            )
        }
    }
}
