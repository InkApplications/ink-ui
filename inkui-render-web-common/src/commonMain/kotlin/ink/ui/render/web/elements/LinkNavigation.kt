package ink.ui.render.web.elements

import ink.ui.structures.elements.UiElement

data class LinkNavigation(
    val items: List<NavigationItem>,
): UiElement.Static {
    constructor(vararg items: NavigationItem): this(items.toList())
    constructor(builder: Builder.() -> Unit): this(Builder().apply(builder).items)

    data class NavigationItem(
        val text: String,
        val url: String,
    )

    data class Builder(
        val items: MutableList<NavigationItem> = mutableListOf(),
    ) {
        fun link(text: String, url: String) {
            items.add(NavigationItem(text = text, url = url))
        }
    }
}
