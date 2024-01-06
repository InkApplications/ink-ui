package ink.ui.render.compose.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.compose.elements.ProgressBar
import ink.ui.render.compose.elements.Throbber
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.ProgressElement
import ink.ui.structures.elements.ThrobberElement
import ink.ui.structures.elements.UiElement

internal object ActivityRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        when (element) {
            is ProgressElement -> ProgressBar(
                element = element,
                theme = theme
            )
            is ThrobberElement -> Throbber(
                caption = element.caption,
                sentiment = element.sentiment,
                theme = theme
            )
            else -> return RenderResult.NotRendered
        }
        return RenderResult.Rendered
    }
}
