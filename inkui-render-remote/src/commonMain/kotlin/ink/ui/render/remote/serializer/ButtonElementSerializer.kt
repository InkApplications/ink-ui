package ink.ui.render.remote.serializer

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.event.EventResult
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.OnContextClickEvent
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEventListener
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.render.remote.serialization.event.typedListener
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.Action
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.elements.UrlHandler
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
            sentiment = surrogate.sentiment,
            enabled = surrogate.enabled,
            latchOnPress = surrogate.latchOnPress,
            leadingSymbol = surrogate.leadingSymbol,
            trailingSymbol = surrogate.trailingSymbol,
            onClick = Action.Handler {
                events.onEvent(OnClickEvent(surrogate.id))
            },
            onContextClick = Action.Handler {
                events.onEvent(OnContextClickEvent(surrogate.id))
            },
        )
    }

    @Serializable
    @SerialName("button")
    data class ButtonSurrogate(
        val id: ElementId = ElementId(),
        val text: String,
        val sentiment: Sentiment,
        val enabled: Boolean,
        @SerialName("latch-on-press")
        val latchOnPress: Boolean,
        @Contextual
        @SerialName("leading-symbol")
        val leadingSymbol: Symbol?,
        @Contextual
        @SerialName("trailing-symbol")
        val trailingSymbol: Symbol?,
    )

    object Listeners
    {
        class OnClickListener(
            private val urlHandler: UrlHandler?,
        ): UiEventListener {
            override suspend fun handleEvent(
                element: UiElement,
                event: UiEvent,
            ): EventResult {
                if (element !is ButtonElement || event !is OnClickEvent) {
                    return EventResult.Continue
                }

                when (val onClick = element.onClick) {
                    is Action.Handler -> onClick.onInvoke()
                    is Action.Link if urlHandler != null -> urlHandler.openUrl(onClick.url)
                    else -> return EventResult.Continue
                }

                return EventResult.Consume
            }
        }

        class OnContextClickListener(
            private val urlHandler: UrlHandler?,
        ): UiEventListener {
            override suspend fun handleEvent(
                element: UiElement,
                event: UiEvent,
            ): EventResult {
                if (element !is ButtonElement || event !is OnContextClickEvent) {
                    return EventResult.Continue
                }

                when (val onContextClick = element.onContextClick) {
                    is Action.Handler -> onContextClick.onInvoke()
                    is Action.Link if urlHandler != null -> urlHandler.openUrl(onContextClick.url)
                    else -> return EventResult.Continue
                }

                return EventResult.Consume
            }
        }
    }
}
