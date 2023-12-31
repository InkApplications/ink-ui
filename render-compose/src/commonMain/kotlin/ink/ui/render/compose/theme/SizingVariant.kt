package ink.ui.render.compose.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Specifies the size of different visual widgets or styles.
 */
data class SizingVariant(
    /**
     * Width of borders used in UI elements like buttons.
     */
    val borders: Dp = 1.dp,

    /**
     * Standard corner radius used for UI elements.
     */
    val corners: Dp = 4.dp,
)
