package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.Orientation

internal fun spacing(
    orientation: Orientation,
    groupingStyle: GroupingStyle,
    isLast: Boolean,
) {
    when (orientation) {
        Orientation.Horizontal -> when (groupingStyle) {
            GroupingStyle.Items -> if (!isLast) print(" ")
            GroupingStyle.Sections -> if (!isLast) print (" | ")
            GroupingStyle.Unified, GroupingStyle.Inline -> {}
        }
        Orientation.Vertical -> when (groupingStyle) {
            GroupingStyle.Sections -> {
                if (!isLast) {
                    print("\n")
                    println(TextColors.gray("=".repeat(80)))
                }
            }
            GroupingStyle.Items, GroupingStyle.Unified -> if (!isLast) print("\n")
            GroupingStyle.Inline -> {}
        }
    }
}
