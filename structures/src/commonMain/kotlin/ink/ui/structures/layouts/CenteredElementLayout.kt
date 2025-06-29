package ink.ui.structures.layouts

import ink.ui.structures.elements.UiElement
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Full-screen layout for a single element.
 *
 * This is typically used as a splash screen.
 */
@Serializable
@SerialName("centered-element")
data class CenteredElementLayout(
    @Contextual
    val body: UiElement,
): UiLayout
