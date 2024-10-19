package ink.ui.structures.elements

data class BreadcrumbElement(
    val items: List<Breadcrumb>,
): UiElement.Static {
    constructor(
        builder: Builder.() -> Unit,
    ): this(
        items = Builder().apply(builder).breadcrumbs,
    )

    data class Breadcrumb(
        val text: String,
        val url: String? = null,
    )

    data class Builder(
        val breadcrumbs: MutableList<Breadcrumb> = mutableListOf(),
    ) {
        fun text(text: String) {
            breadcrumbs.add(Breadcrumb(text = text))
        }

        fun link(text: String, url: String) {
            breadcrumbs.add(Breadcrumb(text = text, url = url))
        }
    }
}
