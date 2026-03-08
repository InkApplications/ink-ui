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

    /**
     * Sizing for icons used in a hint-context such as a button or
     * informational status.
     *
     * These are less prominent than [widgetIcons], and are explained in
     * context other than the icon itself.
     */
    val hintIcons: Dp = 24.dp,

    /**
     * Sizing for informational widget icons such as weather iconography.
     *
     * These icons convey important information that is not provided through
     * other context, and thus are more prominent than [hintIcons].
     */
    val widgetIcons: Dp = 56.dp,
)
