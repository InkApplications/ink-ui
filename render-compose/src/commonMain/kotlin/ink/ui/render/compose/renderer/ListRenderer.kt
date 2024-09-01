package ink.ui.render.compose.renderer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Positioning
import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.Orientation
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

internal object ListRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        if (element !is ElementList) return RenderResult.Skipped

        when (element.orientation) {
            Orientation.Vertical -> Column(
                horizontalAlignment = when (element.positioning) {
                    Positioning.Start -> Alignment.Start
                    Positioning.Center -> Alignment.CenterHorizontally
                },
            ) {
                renderElements(element, theme, parent)
            }
            Orientation.Horizontal -> Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = when (element.positioning) {
                    Positioning.Start -> Arrangement.Start
                    Positioning.Center -> Arrangement.Center
                },
                modifier = Modifier.let {
                    if (element.positioning == Positioning.Center) {
                        it.fillMaxWidth()
                    } else it
                }
            ) {
                renderElements(element, theme, parent)
            }
        }
        return RenderResult.Rendered
    }

    @Composable
    private fun renderElements(
        element: ElementList,
        theme: ComposeRenderTheme,
        parent: ElementRenderer
    ) {
        element.items.forEachIndexed { index, item ->
            parent.render(item, theme, parent)
            if (element.groupingStyle != GroupingStyle.Unified && index != 0 && index != element.items.size - 1) {
                val spacing = when (element.groupingStyle) {
                    GroupingStyle.Items -> theme.spacing.item
                    GroupingStyle.Sections -> theme.spacing.sectionSpacing
                    GroupingStyle.Unified -> throw IllegalStateException()
                }
                Spacer(modifier = Modifier.height(spacing))
            }
        }
    }
}
