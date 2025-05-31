package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.StatusIndicatorElement

val StatusRenderer = renderer<StatusIndicatorElement> {
    val glyph = " ■ "
    when (element.sentiment) {
        Sentiment.Positive -> println(
            "${TextColors.green(glyph)} ${element.text}"
        )
        Sentiment.Nominal -> println(
            "$glyph ${element.text}"
        )
        Sentiment.Negative -> println(
            "${TextColors.red(glyph)} ${element.text}"
        )
        Sentiment.Primary -> println(
            "${TextColors.magenta(glyph)} ${element.text}"
        )
        Sentiment.Caution -> println(
            "${TextColors.yellow(glyph)} ${element.text}"
        )
        Sentiment.Idle -> println(
            "${TextColors.gray(glyph)} ${element.text}"
        )
    }
}
