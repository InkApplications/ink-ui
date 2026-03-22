package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyle
import ink.ui.structures.elements.Action
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

object ButtonRenderer: ElementRenderer
{
    override suspend fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is ink.ui.structures.elements.ButtonElement) return RenderResult.Skipped

        val onClick = element.onClick
        val onContextClick = element.onContextClick

        if (onClick != null && onClick !is Action.Link) {
            return RenderResult.Skipped
        }
        if (onContextClick != null) {
            return RenderResult.Skipped
        }

        val url = onClick?.url.orEmpty()
        val link = TextStyle(
            color = TextColors.blue.color,
            hyperlink = url,
        )

        val prefix = element.leadingSymbol
            ?.let { getAnsiGlyph(it, element.sentiment) }
            ?.let { "$it " }
            .orEmpty()
        val suffix = element.trailingSymbol
            ?.let { getAnsiGlyph(it, element.sentiment) }
            ?.let { " $it" }
            .orEmpty()

        val leftEdge = element.sentiment.formatAnsi("[")
        val rightEdge = element.sentiment.formatAnsi("]")

        print("$leftEdge$prefix${link(element.text)}$suffix$rightEdge")

        return RenderResult.Rendered
    }
}
