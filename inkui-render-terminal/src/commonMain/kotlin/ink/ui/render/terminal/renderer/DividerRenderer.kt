package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import ink.ui.structures.elements.DividerElement

val DividerRenderer = renderer<DividerElement> {
    print(TextColors.gray("-".repeat(80)))
}
