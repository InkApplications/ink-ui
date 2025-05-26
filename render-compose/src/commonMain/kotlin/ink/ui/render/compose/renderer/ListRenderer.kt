package ink.ui.render.compose.renderer

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Positioning
import ink.ui.structures.GroupingStyle.*
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.Orientation
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderWithType

internal object ListRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        return element.renderWithType<ElementList> {
            when (orientation) {
                Orientation.Vertical -> Column(
                    horizontalAlignment = when (positioning) {
                        Positioning.Start -> Alignment.Start
                        Positioning.Center -> Alignment.CenterHorizontally
                    },
                ) {
                    renderElements(this@renderWithType, theme, parent)
                }
                Orientation.Horizontal -> Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = when (positioning) {
                        Positioning.Start -> Arrangement.Start
                        Positioning.Center -> Arrangement.Center
                    },
                    modifier = Modifier.let {
                        if (positioning == Positioning.Center) {
                            it.fillMaxWidth()
                        } else it
                    }
                ) {
                    renderElements(this@renderWithType, theme, parent)
                }
            }
        }
    }

    @Composable
    private fun renderElements(
        element: ElementList,
        theme: ComposeRenderTheme,
        parent: ElementRenderer
    ) {
        element.items.forEachIndexed { index, item ->
            parent.render(item, theme, parent)
            if (element.groupingStyle !in setOf(Unified, Inline) && index != 0 && index != element.items.size - 1) {
                val spacing = when (element.groupingStyle) {
                    Items -> theme.spacing.item
                    Sections -> theme.spacing.sectionSpacing
                    Unified, Inline -> throw IllegalStateException()
                }
                Spacer(modifier = Modifier.height(spacing))
            }
        }
    }
}
