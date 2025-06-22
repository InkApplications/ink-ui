package ink.ui.render.remote.serializer

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.structures.elements.ButtonElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal class ButtonElementSerializer(
    private val events: UiEvents,
): KSerializer<ButtonElement> {
    private val surrogateSerializer = ButtonSurrogate.serializer()
    override val descriptor: SerialDescriptor = surrogateSerializer.descriptor

    override fun serialize(
        encoder: Encoder,
        value: ButtonElement,
    ) {
        val surrogate = ButtonSurrogate(
            text = value.text,
        )
        events.associateElementEvents(surrogate.id, value)
        surrogateSerializer.serialize(encoder, surrogate)
    }

    override fun deserialize(decoder: Decoder): ButtonElement
    {
        val surrogate = surrogateSerializer.deserialize(decoder)
        return ButtonElement(
            text = surrogate.text,
            onClick = {
                events.onEvent(OnClickEvent(surrogate.id))
            }
        )
    }

    @Serializable
    @SerialName("button")
    data class ButtonSurrogate(
        val id: ElementId = ElementId(),
        val text: String,
    )
}
