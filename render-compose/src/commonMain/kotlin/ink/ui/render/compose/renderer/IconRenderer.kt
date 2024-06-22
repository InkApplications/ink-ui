package ink.ui.render.compose.renderer

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ColorFilter
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.IconElement
import ink.ui.structures.elements.UiElement
import org.jetbrains.compose.resources.painterResource

object IconRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        if (element !is IconElement) return RenderResult.NotRendered

        Image(
            painterResource(element.symbol.resource),
            colorFilter = ColorFilter.tint(theme.colors.forSentiment(element.sentiment)),
            contentDescription = null,
        )

        return RenderResult.Rendered
    }
}
