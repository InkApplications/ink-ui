package ink.ui.render.compose.renderer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.EmptyElement
import ink.ui.structures.elements.UiElement

internal object EmptyRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        when (element) {
            is EmptyElement -> Spacer(Modifier)
            else -> return RenderResult.NotRendered
        }
        return RenderResult.Rendered
    }
}
