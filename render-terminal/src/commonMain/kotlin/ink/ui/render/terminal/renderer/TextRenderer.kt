package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.TextElement

val TextRenderer = renderer<TextElement> {
    when (element.style) {
        TextStyle.H1 -> print(
            TextStyles.bold("# ${element.text}")
        )
        TextStyle.H2 -> print(
            TextColors.blue(TextStyles.bold("## ${element.text}"))
        )
        TextStyle.H3 -> print(
            TextColors.blue("### ${element.text}")
        )
        TextStyle.Caption -> print(
            TextColors.gray("(${element.text})")
        )
        TextStyle.Body -> print(element.text)
    }
}
