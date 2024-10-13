package ink.ui.render.web.elements

import ink.ui.structures.elements.UiElement

data class LinkNavigation(
    val items: List<NavigationItem>,
): UiElement.Static {
    constructor(vararg items: NavigationItem): this(items.toList())

    data class NavigationItem(
        val text: String,
        val url: String,
    )
}
