package ink.ui.structures.layouts

import ink.ui.structures.elements.UiElement
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Standard top-to-bottom page layout.
 */
@Deprecated("Use ScrollingListLayout instead.")
@Serializable
@SerialName("page")
data class PageLayout(
    @Contextual
    val body: UiElement,
): UiLayout
