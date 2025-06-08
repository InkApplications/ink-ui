package example

import ink.ui.structures.elements.BreadcrumbElement
import ink.ui.structures.elements.items

val Breadcrumbs = items(
    BreadcrumbElement {
        link("Home", "https://asdf.com")
        text("Breadcrumbs")
    }
)
