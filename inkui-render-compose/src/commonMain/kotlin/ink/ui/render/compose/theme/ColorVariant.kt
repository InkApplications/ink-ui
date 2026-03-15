package ink.ui.render.compose.theme

import androidx.compose.ui.graphics.Color
import ink.ui.structures.Sentiment

/**
 * Colors used in rendered elements.
 */
data class ColorVariant(
    val foreground: Color,
    val foregroundSubtle: Color,
    val background: Color,
    val surface: Color,
    val primary: Color,
    val positive: Color,
    val negative: Color,
    val caution: Color,
    val idle: Color,
    val highlight: Color,
    val heavyHighlight: Color,
    val shadow: Color,
    val heavyShadow: Color,
) {
    fun forSentiment(sentiment: Sentiment): Color = when (sentiment) {
        Sentiment.Positive -> positive
        Sentiment.Negative -> negative
        Sentiment.Caution -> caution
        Sentiment.Nominal -> foreground
        Sentiment.Primary -> primary
        Sentiment.Idle -> idle
    }

    object Defaults
    {
        object Colors
        {
            val BLACK = Color(0xFF000000)
            val WHITE = Color(0xFFFFFFFF)
            val SOFT_WHITE = Color(0xFFF8F8F8)
            val SOFT_BLACK = Color(0xFF212121)
            val GRAY = Color(0xFF90A4AE)
            val SUBTLE_GRAY = Color(0xFF9E9E9E)
            val SOFT_GRAY = Color(0xFF323232)
            val RED = Color(0xFFf92772)
            val GREEN = Color(0xFF00a600)
            val ORANGE = Color(0xFFfe9720)
            val YELLOW = Color(0xFFe3c52d)
            val BLUE = Color(0xFF008DA9)
            val DARK_BLUE = Color(0xFF00A6C7)
            val PURPLE = Color(0xFFa175c9)
        }

        val light = ColorVariant(
            foreground = Colors.SOFT_BLACK,
            foregroundSubtle = Colors.SUBTLE_GRAY,
            background = Colors.WHITE,
            surface = Colors.SOFT_WHITE,
            primary = Colors.BLUE,
            positive = Colors.GREEN,
            negative = Colors.RED,
            caution = Colors.ORANGE,
            idle = Colors.GRAY,
            highlight = Color(0xBFFFFFFF),
            heavyHighlight = Color(0xF2FFFFFF),
            shadow = Color(0x08000000),
            heavyShadow = Color(0x33000000),
        )

        val dark = ColorVariant(
            foreground = Colors.WHITE,
            foregroundSubtle = Color(0xFF808080),
            background = Colors.SOFT_BLACK,
            surface = Colors.SOFT_GRAY,
            primary = Colors.DARK_BLUE,
            positive = Colors.GREEN,
            negative = Colors.RED,
            caution = Colors.ORANGE,
            idle = Colors.GRAY,
            highlight = Color(0x0AFFFFFF),
            heavyHighlight = Color(0x1AFFFFFF),
            shadow = Color(0x4D000000),
            heavyShadow = Color(0x99000000),
        )
    }
}
