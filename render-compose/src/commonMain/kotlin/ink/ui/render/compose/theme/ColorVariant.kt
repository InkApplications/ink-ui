package ink.ui.render.compose.theme

import androidx.compose.ui.graphics.Color
import ink.ui.structures.Sentiment

/**
 * Colors used in rendered elements.
 */
data class ColorVariant(
    val foreground: Color,
    val background: Color,
    val surface: Color,
    val primary: Color,
    val positive: Color,
    val negative: Color,
    val caution: Color,
    val idle: Color,
    val surfaceInteraction: Color = surface,
) {
    val inactive = foreground.copy(alpha = 0.5f)

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
            val SOFT_GRAY = Color(0xFF323232)
            val RED = Color(0xFFe82a70)
            val GREEN = Color(0xFF00a600)
            val ORANGE = Color(0xFFfe9720)
            val YELLOW = Color(0xFFe3c52d)
            val BLUE = Color(0xFF008DA9)
            val PURPLE = Color(0xFFa175c9)
        }

        val light = ColorVariant(
            foreground = Colors.SOFT_BLACK,
            background = Colors.WHITE,
            surface = Colors.WHITE,
            primary = Colors.BLUE,
            positive = Colors.GREEN,
            negative = Colors.RED,
            caution = Colors.ORANGE,
            idle = Colors.GRAY,
        ).let {
            it.copy(
                surfaceInteraction = it.surface.darken(0.1f),
            )
        }

        val dark = light.copy(
            foreground = Colors.WHITE,
            background = Colors.SOFT_BLACK,
            surface = Colors.SOFT_GRAY,
        ).let {
            it.copy(
                surfaceInteraction = it.surface.brighten(0.1f),
            )
        }
    }
}
