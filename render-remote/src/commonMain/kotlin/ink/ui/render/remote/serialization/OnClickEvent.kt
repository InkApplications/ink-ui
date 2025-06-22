package ink.ui.render.remote.serialization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("onclick")
data class OnClickEvent(
    override val id: ElementId,
): UiEvent
