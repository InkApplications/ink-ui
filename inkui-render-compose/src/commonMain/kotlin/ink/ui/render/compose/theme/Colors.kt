package ink.ui.render.compose.theme

import androidx.compose.ui.graphics.Color
import kotlin.math.max
import kotlin.math.min

/**
 * Brighten the color by a flat percentage.
 */
fun Color.brighten(factor: Float): Color {
    return copy(
        red = min(red + factor, 1.0f),
        green = min(green + factor, 1.0f),
        blue = min(blue + factor, 1.0f),
    )
}

/**
 * Darken the color by a flat percentage.
 */
fun Color.darken(factor: Float): Color {
    return copy(
        red = max(red - factor, 0.0f),
        green = max(green - factor, 0.0f),
        blue = max(blue - factor, 0.0f),
    )
}
