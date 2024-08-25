package ink.ui.render.compose.html.renderer

import ink.ui.structures.elements.CheckBoxElement
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.Input

val CheckBoxRenderer = renderer<CheckBoxElement> { element ->
    Input(
        type = InputType.Checkbox,
        attrs = {
            onClick { element.onClick() }
            checked(element.checked)
        }
    )
}
