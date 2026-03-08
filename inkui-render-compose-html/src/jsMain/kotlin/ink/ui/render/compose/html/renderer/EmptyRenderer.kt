package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.EmptyElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import org.jetbrains.compose.web.dom.Span

object EmptyRenderer: ElementRenderer
{
    @Composable
    override fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element != EmptyElement) return RenderResult.Skipped
        Span {}
        return RenderResult.Rendered
    }
}
