package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.ProgressElement
import ink.ui.structures.elements.ThrobberElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlin.math.roundToInt

object ProgressRenderer: ElementRenderer {
    override suspend fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        when (element) {
            is ProgressElement.Determinate -> {
                val filled = "=".repeat((element.progress * 10).roundToInt())
                val empty = " ".repeat(10 - filled.length)
                print("[${element.sentiment.color(filled)}$empty] ${element.progress.times(100).roundToInt()}% ${element.caption.orEmpty()}...")
            }
            is ProgressElement.Indeterminate -> {
                print("[${element.sentiment.color("-  -  -  -")}] ${element.caption ?: "Loading"}...")
            }
            is ThrobberElement -> {
                print("${element.sentiment.color("%%")} ${element.caption.orEmpty()}...")
            }
            else -> {
                return RenderResult.Skipped
            }
        }
        return RenderResult.Rendered
    }
}
