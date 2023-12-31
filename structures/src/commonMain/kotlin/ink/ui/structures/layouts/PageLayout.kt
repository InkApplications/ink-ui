package ink.ui.structures.layouts

import ink.ui.structures.elements.UiElement

/**
 * Standard top-to-bottom page layout.
 */
data class PageLayout(
    val body: UiElement,
): UiLayout
