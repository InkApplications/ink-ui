package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.EmptyElement
import ink.ui.structures.elements.UiElement
import org.jetbrains.compose.web.dom.Span

object EmptyRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is EmptyElement) return RenderResult.NotRendered

        Span {}

        return RenderResult.Rendered
    }
}
