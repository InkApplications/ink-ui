package ink.ui.render.compose.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Button gradient brush varied by pressed state.
 */
fun ComposeRenderTheme.buttonGradient(pressed: Boolean): Brush
{
    return Brush.verticalGradient(
        colors = if (pressed) listOf(
            colors.shadow,
            colors.highlight,
        ) else listOf(
            colors.highlight,
            colors.shadow,
        ),
    )
}

/**
 * Applies the button surface depending on enabled state.
 */
fun Modifier.buttonSurface(
    theme: ComposeRenderTheme,
    shape: Shape,
    gradient: Brush,
    borderColor: Color,
    enabled: Boolean = true,
): Modifier {
    return this
        .clip(shape)
        .let {
            if (enabled) it.border(theme.sizing.borders, borderColor, shape)
            else it
        }
        .background(theme.colors.surface)
        .let {
            if (enabled) it.background(gradient)
            else it
        }
}
