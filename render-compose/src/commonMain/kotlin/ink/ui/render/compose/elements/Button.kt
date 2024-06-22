package ink.ui.render.compose.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import ink.ui.render.compose.renderer.resource
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun Button(
    text: String,
    sentiment: Sentiment = Sentiment.Nominal,
    latching: Boolean = false,
    leadingSymbol: Symbol? = null,
    trailingSymbol: Symbol? = null,
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
        Row(
            verticalAlignment = Alignment.Top,
        ) {
            if (leadingSymbol != null) {
                Image(
                    painterResource(leadingSymbol.resource),
                    colorFilter = ColorFilter.tint(theme.colors.forSentiment(sentiment)),
                    contentDescription = null,
                    modifier = Modifier.padding(end = theme.spacing.item),
                )
            }
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
            if (trailingSymbol != null) {
                Image(
                    painterResource(trailingSymbol.resource),
                    colorFilter = ColorFilter.tint(theme.colors.forSentiment(sentiment)),
                    contentDescription = null,
                    modifier = Modifier.padding(start = theme.spacing.item),
                )
            }
        }
    }
}
