package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.SpinnerElement
import ink.ui.structures.elements.UiElement
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.dom.*

object SpinnerRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is SpinnerElement) return RenderResult.NotRendered

        Div(
            attrs = {
                classes("spinner")
            }
        ) {
            Button(
                attrs = {
                    if (element.hasPreviousValue) {
                        onClick { element.onPreviousValue }
                    } else {
                        disabled()
                    }
                }
            ) {
                Text("<")
            }

            Span(
                attrs = {
                    classes("spinner-value")
                }
            ) {
                Text(element.value)
            }

            Button(
                attrs = {
                    if (element.hasNextValue) {
                        onClick { element.onNextValue }
                    } else {
                        disabled()
                    }
                }
            ) {
                Text(">")
            }
        }

        return RenderResult.Rendered
    }
}
