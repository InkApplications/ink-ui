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

    object Defaults {
        val light = ColorVariant(
            foreground = Color(0xFF212121),
            background = Color(0xFFF8F8F8),
            surface = Color(0xFFFFFFFF),
            primary = Color(0xFF008DA9),
            positive = Color(0xFF00a600),
            negative = Color(0xFFf92772),
            caution = Color(0xFFfe9720),
            idle = Color(0xFF90A4AE),
        ).let {
            it.copy(
                surfaceInteraction = it.surface.darken(0.1f),
            )
        }

        val dark = light.copy(
            foreground = Color(0xFFFFFFFF),
            background = Color(0xFF212121),
            surface = Color(0xFF323232),
        ).let {
            it.copy(
                surfaceInteraction = it.surface.brighten(0.1f),
            )
        }
    }
}
