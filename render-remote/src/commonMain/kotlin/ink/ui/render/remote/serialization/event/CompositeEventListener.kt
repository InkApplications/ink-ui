package ink.ui.render.remote.serialization.event

import ink.ui.structures.elements.UiElement
import kotlin.jvm.JvmInline

/**
 * Combines multiple event listeners.
 *
 * This will invoke each listener in order until one consumes the event,
 * otherwise it will also continue.
 */
@JvmInline
value class CompositeEventListener(
    val listeners: List<UiEventListener>
): UiEventListener {
    override suspend fun handleEvent(element: UiElement, event: UiEvent): EventResult {
        listeners.forEach { listener ->
            val result = listener.handleEvent(element, event)
            if (result is EventResult.Consume) {
                return result
            }
        }
        return EventResult.Continue
    }
}

/**
 * Combine two listeners, preserving the order.
 */
operator fun UiEventListener.plus(other: UiEventListener): UiEventListener
{
    if (this is CompositeEventListener && other is CompositeEventListener) {
        return CompositeEventListener(this.listeners + other.listeners)
    }
    return CompositeEventListener(listOf(this, other))
}
