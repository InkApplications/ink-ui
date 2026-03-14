package ink.ui.render.remote.serialization

import ink.ui.structures.elements.UiElement
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Wraps a serialized layout with extra metadata.
 */
@Serializable
data class ElementMessage(
    @Contextual
    val element: UiElement? = null,
    val encoding: LayoutEncoding = LayoutEncoding.Plain,
)
