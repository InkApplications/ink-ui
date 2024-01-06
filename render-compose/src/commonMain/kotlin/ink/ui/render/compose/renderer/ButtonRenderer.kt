package ink.ui.render.compose.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.compose.elements.Button
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.UiElement

internal object ButtonRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        when (element) {
            is ButtonElement -> Button(
                text = element.text,
                sentiment = element.sentiment,
                latching = element.latchOnPress,
                theme = theme,
                onClick = element.onClick,
            )
            else -> return RenderResult.NotRendered
        }
        return RenderResult.Rendered
    }
}
