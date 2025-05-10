package ink.ui.structures.layouts

import ink.ui.structures.elements.UiElement

/**
 * Standard top-to-bottom page layout.
 */
@Deprecated("Use ScrollingListLayout instead.")
data class PageLayout(
    val body: UiElement,
): UiLayout
