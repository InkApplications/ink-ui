package ink.ui.render.statichtml.renderer

import ink.ui.render.web.svgSrc
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.Action
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.TagConsumer
import kotlinx.html.a
import kotlinx.html.img

class ButtonRenderer(
    private val iconPath: String?
): ElementRenderer {
    override fun TagConsumer<*>.render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is ink.ui.structures.elements.ButtonElement) return RenderResult.Skipped

        val onClick = element.onClick
        val onContextClick = element.onContextClick

        if (onClick != null && onClick !is Action.Link) {
            return RenderResult.Skipped
        }
        if (onContextClick != null) {
            return RenderResult.Skipped
        }

        a(
            href = onClick?.url.orEmpty(),
            classes = listOf("button", element.sentiment.toCssClass()).joinToString(" "),
        ) {
            val leadingSymbol = element.leadingSymbol
            val trailingSymbol = element.trailingSymbol
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
