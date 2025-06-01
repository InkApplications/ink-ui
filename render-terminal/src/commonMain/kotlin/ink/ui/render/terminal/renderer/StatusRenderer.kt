package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.StatusIndicatorElement

val StatusRenderer = renderer<StatusIndicatorElement> {
    val glyph = " ■ "
    when (element.sentiment) {
        Sentiment.Positive -> print(
            "${TextColors.green(glyph)} ${element.text}"
        )
        Sentiment.Nominal -> print(
            "$glyph ${element.text}"
        )
        Sentiment.Negative -> print(
            "${TextColors.red(glyph)} ${element.text}"
        )
        Sentiment.Primary -> print(
            "${TextColors.magenta(glyph)} ${element.text}"
        )
        Sentiment.Caution -> print(
            "${TextColors.yellow(glyph)} ${element.text}"
        )
        Sentiment.Idle -> print(
            "${TextColors.gray(glyph)} ${element.text}"
        )
    }
}
