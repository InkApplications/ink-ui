package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import ink.ui.structures.Sentiment

fun Sentiment.color(output: String): String
{
    return when (this) {
        Sentiment.Positive -> TextColors.green(output)
        Sentiment.Nominal -> output
        Sentiment.Negative -> TextColors.red(output)
        Sentiment.Primary -> TextColors.magenta(output)
        Sentiment.Caution -> TextColors.yellow(output)
        Sentiment.Idle -> TextColors.gray(output)
    }
}
