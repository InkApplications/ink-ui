package ink.ui.render.remote.serialization

import ink.ui.structures.layouts.UiLayout
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * Wraps a serialized layout with extra metadata.
 */
@Serializable
data class LayoutMessage(
    @Contextual
    val layout: UiLayout? = null,
    val encoding: LayoutEncoding = LayoutEncoding.Plain,
)
