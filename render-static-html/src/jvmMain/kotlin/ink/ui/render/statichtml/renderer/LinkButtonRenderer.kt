package ink.ui.render.statichtml.renderer

import ink.ui.render.web.elements.LinkButtonElement
import ink.ui.render.web.svgSrc
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.*

class LinkButtonRenderer(
    private val iconPath: String?
): ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is LinkButtonElement) return RenderResult.Skipped
        val leadingSymbol = element.leadingSymbol
        val trailingSymbol = element.trailingSymbol

        a(
            href = element.url,
            classes = listOf("button", element.sentiment.toCssClass()).joinToString(" ")
        ) {
            if (leadingSymbol != null) {
                img(
                    classes = "icon svg-fill ${element.sentiment.toCssClass()}",
                    src = "${iconPath}/${leadingSymbol.svgSrc}",
                )
            }
            +element.text
            if (trailingSymbol != null) {
                img(
                    classes = "icon svg-fill ${element.sentiment.toCssClass()}",
                    src = "${iconPath}/${trailingSymbol.svgSrc}",
                )
            }
        }

        return RenderResult.Rendered
    }
}
