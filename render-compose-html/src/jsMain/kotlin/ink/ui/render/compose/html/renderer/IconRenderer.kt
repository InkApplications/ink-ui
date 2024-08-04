package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.IconElement
import ink.ui.structures.elements.UiElement
import org.jetbrains.compose.web.dom.Img

object IconRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is IconElement) return RenderResult.NotRendered

        Img(
            src = element.symbol.svgSrc,
            attrs = {
                classes("icon", "svg-fill", element.sentiment.toCssClass())
            }
        )

        return RenderResult.Rendered
    }
}
