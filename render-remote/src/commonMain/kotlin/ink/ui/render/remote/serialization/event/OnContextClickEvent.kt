package ink.ui.render.remote.serialization.event

import ink.ui.render.remote.serialization.ElementId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("contextclick")
data class OnContextClickEvent(
    override val id: ElementId,
): UiEvent
