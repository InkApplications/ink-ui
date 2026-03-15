package ink.ui.render.compose.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A box shadow more similar to what you'd get with CSS.
 */
fun Modifier.boxShadow(
    color: Color,
    blur: Dp = 4.dp,
    cornerRadius: Dp = 0.dp,
    layers: Int = 4,
): Modifier = drawBehind {
    val maxSpread = blur.toPx()
    val radius = cornerRadius.toPx()
    val elementPath = Path().apply {
        addRoundRect(RoundRect(0f, 0f, size.width, size.height, CornerRadius(radius)))
    }
    clipPath(elementPath, clipOp = ClipOp.Difference) {
        for (i in 1..layers) {
            val spread = maxSpread * i / layers
            drawRoundRect(
                color = color.copy(alpha = color.alpha / (i * i * 2f)),
                topLeft = Offset(-spread, -spread),
                size = Size(size.width + spread * 2, size.height + spread * 2),
                cornerRadius = CornerRadius(radius + spread),
            )
        }
    }
}


