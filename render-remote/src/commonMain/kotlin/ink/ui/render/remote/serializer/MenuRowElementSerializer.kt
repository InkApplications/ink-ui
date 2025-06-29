package ink.ui.render.remote.serializer

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.structures.elements.MenuRowElement
import ink.ui.structures.elements.UiElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class MenuRowElementSerializer(private val uiEvents: UiEvents): KSerializer<MenuRowElement>
{
    private val surrogateSerializer = MenuRowSurrogate.serializer()
    override val descriptor = surrogateSerializer.descriptor

    override fun deserialize(decoder: Decoder): MenuRowElement
    {
        val surrogate = surrogateSerializer.deserialize(decoder)
        return MenuRowElement(
            text = surrogate.text,
            rightElement = surrogate.rightElement,
            onClick = {
                uiEvents.onEvent(OnClickEvent(surrogate.id))
            }
        )
    }

    override fun serialize(encoder: Encoder, value: MenuRowElement)
    {
        val id = ElementId()
        val surrogate = MenuRowSurrogate(
            id = id,
            text = value.text,
            rightElement = value.rightElement,
        )
        uiEvents.associateElementEvents(surrogate.id, value)
        surrogateSerializer.serialize(encoder, surrogate)
    }

    @Serializable
    @SerialName("menu-row")
    data class MenuRowSurrogate(
        val id: ElementId,
        val text: String,
        @SerialName("right-element")
        val rightElement: UiElement? = null,
    )
}
