package ink.ui.render.compose.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Spacing used when laying out elements.
 */
data class SpacingVariant(
    /**
     * Space between any element and the edge of the screen.
     */
    val gutters: Dp = 16.dp,

    /**
     * Spacing between two elements in the same section.
     */
    val item: Dp = 8.dp,

    /**
     * Padding required around a clickable element.
     */
    val clickSafety: Dp = 16.dp,

    /**
     * Spacing between two distinct sections in the same layout.
     */
    val sectionSpacing: Dp = 16.dp,
)
