package ink.ui.render.compose.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.compose.elements.StatusIndicator
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.StatusIndicatorElement
import ink.ui.structures.elements.UiElement

internal object StatusIndicatorRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        when (element) {
            is StatusIndicatorElement -> StatusIndicator(
                text = element.text,
                sentiment = element.sentiment,
                theme = theme,
            )
            else -> return RenderResult.NotRendered
        }
        return RenderResult.Rendered
    }
}
