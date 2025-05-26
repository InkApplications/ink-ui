package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.StatusIndicatorElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

object StatusRenderer: ElementRenderer {
    override suspend fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is StatusIndicatorElement) return RenderResult.Skipped
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

        return RenderResult.Rendered
    }
}

