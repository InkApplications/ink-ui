package ink.ui.render.remote.serializer

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.OnContextClickEvent
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.ButtonElement
import kotlinx.serialization.Contextual
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
            sentiment = value.sentiment,
            enabled = value.enabled,
            latchOnPress = value.latchOnPress,
            leadingSymbol = value.leadingSymbol,
            trailingSymbol = value.trailingSymbol,
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
            },
            onContextClick = {
                events.onEvent(OnContextClickEvent(surrogate.id))
            },
            sentiment = surrogate.sentiment,
        )
    }

    @Serializable
    @SerialName("button")
    data class ButtonSurrogate(
        val id: ElementId = ElementId(),
        val text: String,
        val sentiment: Sentiment,
        val enabled: Boolean,
        val latchOnPress: Boolean,
        @Contextual
        val leadingSymbol: Symbol?,
        @Contextual
        val trailingSymbol: Symbol?,
    )
}
