package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.UiElement
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

object ButtonRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is ButtonElement) return RenderResult.NotRendered

        Button(
            attrs = {
                classes(element.sentiment.toCssClass())
                onClick {
                    element.onClick()
                }
            }
        ) {
            val leadingSymbol = element.leadingSymbol
            if (leadingSymbol != null) {
                Img(
                    attrs = {
                        classes("icon", "svg-fill", element.sentiment.toCssClass())
                    },
                    src = leadingSymbol.svgSrc,
                )
            }
            Text(element.text)
        }

        return RenderResult.Rendered
    }
}
