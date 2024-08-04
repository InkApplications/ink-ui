package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.CheckBoxElement
import ink.ui.structures.elements.UiElement
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Input

object CheckBoxRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        if (element !is CheckBoxElement) return RenderResult.NotRendered

        Input(
            type = InputType.Checkbox,
            attrs = {
                onClick { element.onClick() }
                checked(element.checked)
            }
        )

        return RenderResult.Rendered
    }
}
