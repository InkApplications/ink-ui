package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyle
import ink.ui.structures.elements.BreadcrumbElement

val BreadCrumbRenderer = renderer<BreadcrumbElement> {
    val itemStyle = TextStyle(
        color = TextColors.magenta,
        italic = true,
    )
    element.items
        .joinToString(TextColors.gray(" > ")) {
            it.text.let { itemStyle(it) }
        }
        .forEach { print(it) }
}
