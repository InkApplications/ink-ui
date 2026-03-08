package ink.ui.structures.elements

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * UI Element that does nothing. Can be used as a default when no element
 * should be shown, or in grids to fill blank space.
 */
@Serializable
@SerialName("empty")
data object EmptyElement: UiElement.Static
