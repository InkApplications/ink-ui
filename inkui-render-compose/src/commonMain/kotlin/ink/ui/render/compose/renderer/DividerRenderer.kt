package ink.ui.render.compose.renderer

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import ink.ui.structures.elements.DividerElement

val DividerRenderer = renderer<DividerElement> { theme, element ->
    Canvas(
        modifier = Modifier.padding(vertical = theme.spacing.item)
            .fillMaxWidth()
            .height(theme.sizing.borders)
    ) {
        val dotRadius = theme.sizing.borders.toPx()
        val gap = dotRadius.times(2)
        var x = 0f
        while (x < size.width) {
            drawCircle(theme.colors.inactive, dotRadius, center = Offset(x + dotRadius, size.height / 2))
            x += dotRadius * 2 + gap
        }
    }
}
