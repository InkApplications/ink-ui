package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

object TextRenderer: ElementRenderer
{
    override suspend fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is TextElement) return RenderResult.Skipped

        when (element.style) {
            TextStyle.H1 -> println(
                TextStyles.bold("# ${element.text}")
            )
            TextStyle.H2 -> println(
                TextColors.blue(TextStyles.bold("## ${element.text}"))
            )
            TextStyle.H3 -> println(
                TextColors.blue("### ${element.text}")
            )
            TextStyle.Caption -> println(
                TextColors.gray("(${element.text})")
            )
            TextStyle.Body -> println(element.text)
        }
        return RenderResult.Rendered
    }
}

