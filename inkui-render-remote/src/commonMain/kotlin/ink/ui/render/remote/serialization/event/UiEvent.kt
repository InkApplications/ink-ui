package ink.ui.render.remote.serialization.event

import ink.ui.render.remote.serialization.ElementId

/**
 * Associates a UI event with a specific occurrence of an event.
 */
interface UiEvent
{
    val id: ElementId
}
