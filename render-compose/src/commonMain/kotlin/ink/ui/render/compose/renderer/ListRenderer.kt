package ink.ui.render.compose.renderer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement

internal object ListRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        when (element) {
            is ElementList -> Column(
                horizontalAlignment = when (element.positioning) {
                    Positioning.Start -> Alignment.Start
                    Positioning.Center -> Alignment.CenterHorizontally
                },
            ) {
                element.items.forEachIndexed { index, item ->
                    parent.render(item, theme, parent)
                    if (index != 0 && index != element.items.size - 1) {
                        Spacer(modifier = Modifier.height(theme.spacing.item))
                    }
                }
            }
            else -> return RenderResult.NotRendered
        }
        return RenderResult.Rendered
    }
}
