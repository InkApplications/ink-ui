package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("breadcrumb")
data class BreadcrumbElement(
    val items: List<Breadcrumb>,
): UiElement.Static {
    @Serializable
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

    companion object
    {
        fun build(builder: Builder.() -> Unit): BreadcrumbElement
        {
            return BreadcrumbElement(Builder().apply(builder).breadcrumbs)
        }
    }
}

fun ElementContainer.breadcrumbs(builder: BreadcrumbElement.Builder.() -> Unit)
{
    BreadcrumbElement.build(builder).also(::appendElement)
}
