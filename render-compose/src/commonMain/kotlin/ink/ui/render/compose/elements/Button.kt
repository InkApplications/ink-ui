package ink.ui.render.compose.elements

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import ink.ui.render.compose.renderer.resource
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.render.compose.theme.defaultTheme
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun Button(
    text: String,
    sentiment: Sentiment = Sentiment.Nominal,
    latching: Boolean = false,
    leadingSymbol: Symbol? = null,
    trailingSymbol: Symbol? = null,
    theme: ComposeRenderTheme = defaultTheme(),
    onClick: () -> Unit,
    buttonModifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
) {
    var latched by remember(text, sentiment) { mutableStateOf(false) }
    val borderColor = if (latched) theme.colors.inactive else theme.colors.forSentiment(sentiment)
    val latchingClick = {
        if (latching) {
            latched = true
        }
        onClick()
    }
    val haptic = LocalHapticFeedback.current
    val backgroundColor = remember { Animatable(theme.colors.surface) }
    val scope = rememberCoroutineScope()
    var animationJob by remember { mutableStateOf<Job?>(null) }
    Box(
        contentAlignment = Alignment.Center,
        modifier = buttonModifier
            .semantics { role = Role.Button }
            .clip(RoundedCornerShape(theme.sizing.corners))
            .border(theme.sizing.borders, borderColor, RoundedCornerShape(theme.sizing.corners))
            .background(backgroundColor.value)
            .let { if (latched) it else it.clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    animationJob?.cancel()
                    animationJob = scope.launch {
                        backgroundColor.snapTo(theme.colors.surfaceInteraction)
                        backgroundColor.animateTo(theme.colors.surface.copy(), tween(300))
                    }
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    latchingClick()
                }
            )}
            .padding(theme.spacing.clickSafety)
            .then(contentModifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (leadingSymbol != null) {
                Image(
                    painterResource(leadingSymbol.resource),
                    colorFilter = ColorFilter.tint(theme.colors.forSentiment(sentiment)),
                    contentDescription = null,
                    modifier = Modifier.padding(end = theme.spacing.item.takeIf { text.isNotEmpty() } ?: 0.dp),
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
                    modifier = Modifier.padding(start = theme.spacing.item.takeIf { text.isNotEmpty() } ?: 0.dp),
                )
            }
        }
    }
}
