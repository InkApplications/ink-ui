package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.StatusIndicatorElement

val StatusRenderer = renderer<StatusIndicatorElement> {
    when (element.sentiment) {
        Sentiment.Positive -> println(
            "${TextColors.green("•")} ${element.text}"
        )
        Sentiment.Nominal -> println(
            "• ${element.text}"
        )
        Sentiment.Negative -> println(
            "${TextColors.red("•")} ${element.text}"
        )
        Sentiment.Primary -> println(
            "${TextColors.magenta("•")} ${element.text}"
        )
        Sentiment.Caution -> println(
            "${TextColors.yellow("•")} ${element.text}"
        )
        Sentiment.Idle -> println(
            "${TextColors.gray("•")} ${element.text}"
        )
    }
}
