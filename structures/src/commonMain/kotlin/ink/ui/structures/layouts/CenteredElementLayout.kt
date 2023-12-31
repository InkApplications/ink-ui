package ink.ui.structures.layouts

import ink.ui.structures.elements.UiElement

/**
 * Full-screen layout for a single element.
 *
 * This is typically used as a splash screen.
 */
data class CenteredElementLayout(
    val body: UiElement,
): UiLayout
