package ink.ui.structures.elements

data class BreadcrumbElement(
    val items: List<Breadcrumb>,
): UiElement.Static {
    data class Breadcrumb(
        val text: String,
        val url: String? = null,
    )
}
