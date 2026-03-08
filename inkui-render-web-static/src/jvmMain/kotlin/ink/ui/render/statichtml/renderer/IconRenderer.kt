package ink.ui.render.statichtml.renderer

import ink.ui.render.web.svgSrc
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.IconElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.html.*

class IconRenderer(
    private val iconPath: String?
): ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is IconElement) return RenderResult.Skipped
        if (iconPath == null) return RenderResult.Skipped

        img(
            classes = "icon svg-fill ${element.sentiment.toCssClass()}",
            src = "${iconPath}/${element.symbol.svgSrc}",
        )

        return RenderResult.Rendered
    }
}
