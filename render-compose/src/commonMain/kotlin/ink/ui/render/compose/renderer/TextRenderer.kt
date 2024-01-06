package ink.ui.render.compose.renderer

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.UiElement

internal object TextRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        when (element) {
            is TextElement -> BasicText(
                text = element.text,
                style = when (element.style) {
                    TextStyle.H1 -> theme.typography.h1
                    TextStyle.H2 -> theme.typography.h2
                    TextStyle.H3 -> theme.typography.h3
                    TextStyle.Body -> theme.typography.body
                    TextStyle.Caption -> theme.typography.caption
                }.copy(color = theme.colors.foreground),
            )
            else -> return RenderResult.NotRendered
        }
        return RenderResult.Rendered
    }
}
