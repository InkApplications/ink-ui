package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@Serializable
@SerialName("divider")
@JsExport
data object DividerElement: UiElement.Static

fun ElementContainer.divider()
{
    append(DividerElement)
}
