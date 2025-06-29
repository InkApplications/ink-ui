package ink.ui.render.remote.serialization.event

import ink.ui.structures.elements.UiElement

/**
 * Invoked when a serialized UI event occurs.
 *
 * This should be used to map serialized UI events back to their element's
 * corresponding callbacks.
 */
interface UiEventListener
{
    suspend fun handleEvent(element: UiElement, event: UiEvent): EventResult
}

object EmptyUiEventListener: UiEventListener {
    override suspend fun handleEvent(element: UiElement, event: UiEvent): EventResult {
        return EventResult.Continue
    }
}

inline fun UiEventListener.consume(consumer: () -> Unit): EventResult
{
    consumer()

    return EventResult.Consume
}

inline fun UiEventListener.continueAfter(consumer: () -> Unit): EventResult
{
    consumer()

    return EventResult.Continue
}

sealed interface EventResult
{
    data object Consume: EventResult
    data object Continue: EventResult
}

inline fun <reified ELEMENT: UiElement, reified EVENT: UiEvent> typedListener(
    consume: Boolean = true,
    crossinline consumer: (element: ELEMENT, event: EVENT) -> Unit
): UiEventListener {
    return object: UiEventListener {
        override suspend fun handleEvent(element: UiElement, event: UiEvent): EventResult {
            when {
                element is ELEMENT && event is EVENT -> {
                    consumer(element, event)
                    return if (consume) EventResult.Consume else EventResult.Continue
                }
            }
            return EventResult.Continue
        }
    }
}
