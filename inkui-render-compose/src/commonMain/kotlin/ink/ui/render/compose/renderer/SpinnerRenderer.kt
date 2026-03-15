package ink.ui.render.compose.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ink.ui.render.compose.theme.boxShadow
import ink.ui.render.compose.theme.buttonGradient
import ink.ui.render.compose.theme.buttonSurface
import ink.ui.structures.elements.SpinnerElement

internal val SpinnerRenderer = renderer<SpinnerElement> { theme, element ->
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpinnerButton(
            text = "<",
            enabled = element.hasPreviousValue,
            onClick = element.onPreviousValue,
            theme = theme,
        )

        val valueShape = RoundedCornerShape(theme.sizing.corners)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 4.dp)
                .boxShadow(
                    color = theme.colors.heavyShadow,
                    blur = 4.dp,
                    cornerRadius = theme.sizing.corners,
                )
                .border(theme.sizing.borders, theme.colors.heavyHighlight, valueShape)
                .clip(valueShape)
                .background(theme.colors.surface)
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            BasicText(
                text = element.value,
                style = theme.typography.body.copy(
                    color = theme.colors.primary,
                ),
            )
        }

        SpinnerButton(
            text = ">",
            enabled = element.hasNextValue,
            onClick = element.onNextValue,
            theme = theme,
        )
    }
}

@Composable
private fun SpinnerButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    theme: ink.ui.render.compose.theme.ComposeRenderTheme,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val gradientBrush = theme.buttonGradient(pressed = isPressed)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .buttonSurface(
                theme = theme,
                shape = CircleShape,
                gradient = gradientBrush,
                borderColor = theme.colors.foregroundSubtle,
                enabled = enabled,
            )
            .let {
                if (enabled) it.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    onClick = onClick,
                )
                else it
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        BasicText(
            text = text,
            style = theme.typography.body.copy(
                color = if (enabled) theme.colors.foreground else theme.colors.idle,
            ),
        )
    }
}
