package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.StatusIndicatorElement
import ink.ui.structures.elements.UiElement
import kotlinx.html.*

class StatusRenderer(
    private val iconPath: String?
): ElementRenderer {
    override fun TagConsumer<*>.render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is StatusIndicatorElement) return RenderResult.NotRendered
        div(classes = "status-indicator") {
            if(iconPath != null) {
                eagerImg(
                    classes = "status-indicator-blip svg-fill ${element.sentiment.toCssClass()}",
                    src = "$iconPath/blip.svg",
                )
            } else {
                span(classes = "status-indicator-blip ${element.sentiment.toCssClass()}") {
                    +"•"
                }
            }
            span {
                +element.text
            }
        }

        return RenderResult.Rendered
    }
}
