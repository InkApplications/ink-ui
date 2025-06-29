package ink.ui.render.remote.serializer

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.render.remote.serialization.event.typedListener
import ink.ui.structures.elements.CheckBoxElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class CheckBoxElementSerializer(private val uiEvents: UiEvents): KSerializer<CheckBoxElement>
{
    private val surrogateSerializer = CheckBoxSurrogate.serializer()
    override val descriptor = surrogateSerializer.descriptor

    override fun deserialize(decoder: Decoder): CheckBoxElement
    {
        val surrogate = surrogateSerializer.deserialize(decoder)
        return CheckBoxElement(
            checked = surrogate.checked,
            onClick = {
                uiEvents.onEvent(OnClickEvent(surrogate.id))
            },
        )
    }

    override fun serialize(encoder: Encoder, value: CheckBoxElement)
    {
        val id = ElementId()
        val surrogate = CheckBoxSurrogate(
            id = id,
            checked = value.checked,
        )
        uiEvents.associateElementEvents(surrogate.id, value)
        surrogateSerializer.serialize(encoder, surrogate)
    }

    @Serializable
    @SerialName("checkbox")
    data class CheckBoxSurrogate(
        val id: ElementId,
        val checked: Boolean,
    )

    object Listeners
    {
        val OnClickListener = typedListener<CheckBoxElement, OnClickEvent> { element, event ->
            element.onClick()
        }
    }
}
