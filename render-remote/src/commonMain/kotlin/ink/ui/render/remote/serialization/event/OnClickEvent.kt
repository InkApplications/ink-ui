package ink.ui.render.remote.serialization.event

import ink.ui.render.remote.serialization.ElementId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("onclick")
data class OnClickEvent(
    override val id: ElementId,
): UiEvent
