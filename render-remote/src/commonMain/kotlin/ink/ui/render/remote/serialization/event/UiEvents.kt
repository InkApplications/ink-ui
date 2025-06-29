package ink.ui.render.remote.serialization.event

import ink.ui.render.remote.serialization.ElementId
import ink.ui.structures.elements.UiElement
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Container for tracking UI events associated with specific UI elements.
 */
class UiEvents
{
    private val eventBus = MutableSharedFlow<UiEvent>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    private val idRegistrations = Channel<Pair<ElementId, UiElement>>(
        capacity = Channel.Factory.UNLIMITED,
    )

    /**
     * Emits an event when the server element triggers a UI event.
     */
    val events: Flow<UiEvent> = eventBus

    /**
     * Emits an association between a UI element and the serialized id client-side.
     */
    val mappings: ReceiveChannel<Pair<ElementId, UiElement>> = idRegistrations

    fun onEvent(event: UiEvent)
    {
        eventBus.tryEmit(event)
    }

    fun associateElementEvents(id: ElementId, element: UiElement)
    {
        idRegistrations.trySend(id to element)
    }
}
