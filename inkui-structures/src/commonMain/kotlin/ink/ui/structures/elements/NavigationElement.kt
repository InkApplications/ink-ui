package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import kotlinx.serialization.Serializable

@Serializable
data class NavigationElement(
    val items: List<NavigationItem>,
): UiElement.Static {
    constructor(vararg items: NavigationItem): this(items.toList())
    constructor(builder: Builder.() -> Unit): this(Builder().apply(builder).items)

    @Serializable
    data class NavigationItem(
        val text: String,
        val url: String,
        val before: String? = null,
    )

    data class Builder(
        val items: MutableList<NavigationItem> = mutableListOf(),
    ) {
        fun link(text: String, url: String) {
            items.add(NavigationItem(text = text, url = url))
        }
        fun link(before: String, text: String, url: String) {
            items.add(NavigationItem(text = text, url = url, before = before))
        }
    }
}

fun ElementContainer.navigation(builder: NavigationElement.Builder.() -> Unit)
{
    NavigationElement.Builder()
        .also { builder(it) }
        .items
        .let(::NavigationElement)
        .also(::append)
}
