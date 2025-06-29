package ink.ui.render.remote.serialization.event

import ink.ui.render.remote.serialization.ElementId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("next-element")
data class OnNextValue(
    override val id: ElementId,
): UiEvent

@Serializable
@SerialName("previous-element")
data class OnPreviousValue(
    override val id: ElementId,
): UiEvent
