package ink.ui.render.compose.elements

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.GestureCancellationException
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.dp
import ink.ui.render.compose.renderer.resource
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.render.compose.theme.buttonGradient
import ink.ui.render.compose.theme.buttonSurface
import ink.ui.render.compose.theme.defaultTheme
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import kotlinx.coroutines.delay
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
    onLongClick: (() -> Unit)? = null,
    buttonModifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
) {
    var latched by remember(text, sentiment) { mutableStateOf(false) }
    val latchingClick = {
        if (latching) {
            latched = true
        }
        onClick()
    }
    val haptic = LocalHapticFeedback.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val gradientBrush = theme.buttonGradient(pressed = isPressed)
    val shape = RoundedCornerShape(theme.sizing.corners)
    Box(
        contentAlignment = Alignment.Center,
        modifier = buttonModifier
            .semantics { role = Role.Button }
            .shadow(2.dp, shape)
            .buttonSurface(
                theme = theme,
                shape = shape,
                gradient = gradientBrush,
                borderColor = theme.colors.forSentiment(sentiment),
                enabled = !latched,
            )
            .let {
                if (latched) it
                else it.pointerInput(onClick, onLongClick) {
                    detectTapGestures(
                        onPress = { offset ->
                            val press = PressInteraction.Press(offset)
                            interactionSource.emit(press)
                            try {
                                awaitRelease()
                                interactionSource.emit(PressInteraction.Release(press))
                            } catch (cancel: GestureCancellationException) {
                                interactionSource.emit(PressInteraction.Cancel(press))
                            }
                        },
                        onTap = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            latchingClick()
                        },
                        onLongPress = if (onLongClick != null) {
                            {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                onLongClick()
                            }
                        } else null,
                    )
                }
            }
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
                    modifier = Modifier
                        .padding(end = theme.spacing.item.takeIf { text.isNotEmpty() } ?: 0.dp)
                        .size(theme.sizing.hintIcons),
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
                        color = theme.colors.idle,
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
                    modifier = Modifier
                        .padding(start = theme.spacing.item.takeIf { text.isNotEmpty() } ?: 0.dp)
                        .size(theme.sizing.hintIcons),
                )
            }
        }
    }
}
