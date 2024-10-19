package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.BreadcrumbElement
import kotlinx.html.*

val BreadcrumbRenderer = renderer<BreadcrumbElement> { element ->
    nav {
        ul {
            element.items.forEach { breadcrumb ->
                li {
                    if (breadcrumb.url != null) {
                        a(href = breadcrumb.url) { +breadcrumb.text }
                    } else {
                        +breadcrumb.text
                    }
                }
            }
        }
    }
}
