package ink.ui.render.remote.serializer

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.event.EventResult
import ink.ui.render.remote.serialization.event.OnNextValue
import ink.ui.render.remote.serialization.event.OnPreviousValue
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEventListener
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.render.remote.serialization.event.typedListener
import ink.ui.structures.elements.SpinnerElement
import ink.ui.structures.elements.UiElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class SpinnerElementSerializer(private val uiEvents: UiEvents): KSerializer<SpinnerElement>
{
    private val surrogateSerializer = SpinnerSurrogate.serializer()
    override val descriptor = surrogateSerializer.descriptor

    override fun serialize(encoder: Encoder, value: SpinnerElement)
    {
        val id = ElementId()
        val surrogate = SpinnerSurrogate(
            id = id,
            value = value.value,
            hasPreviousValue = value.hasPreviousValue,
            hasNextValue = value.hasNextValue,
        )

        uiEvents.associateElementEvents(surrogate.id, value)
        surrogateSerializer.serialize(encoder, surrogate)
    }

    override fun deserialize(decoder: Decoder): SpinnerElement
    {
        val surrogate = surrogateSerializer.deserialize(decoder)
        return SpinnerElement(
            value = surrogate.value,
            hasPreviousValue = surrogate.hasPreviousValue,
            hasNextValue = surrogate.hasNextValue,
            onPreviousValue = {
                uiEvents.onEvent(OnPreviousValue(surrogate.id))
            },
            onNextValue = {
                uiEvents.onEvent(OnNextValue(surrogate.id))
            },
        )
    }

    @Serializable
    @SerialName("spinner")
    private data class SpinnerSurrogate(
        val id: ElementId,
        val value: String,
        @SerialName("has-previous-value")
        val hasPreviousValue: Boolean = true,
        @SerialName("has-next-value")
        val hasNextValue: Boolean = true,
    )

    object Listeners
    {
        val OnNextValueListener = typedListener<SpinnerElement, OnNextValue> { element, event ->
            element.onNextValue()
        }
        val OnPreviousValueListener = typedListener<SpinnerElement, OnPreviousValue> { element, event ->
            element.onPreviousValue()
        }
    }
}
