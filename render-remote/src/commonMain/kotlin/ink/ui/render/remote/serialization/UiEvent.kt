package ink.ui.render.remote.serialization

import kotlinx.serialization.Serializable

/**
 * Associates a UI event with a specific occurrence of an event.
 */
@Serializable
data class UiEvent(
    val id: ElementId,
    val type: EventType,
)
