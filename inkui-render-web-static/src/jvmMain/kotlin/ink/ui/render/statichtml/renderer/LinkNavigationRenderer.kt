package ink.ui.render.statichtml.renderer

import ink.ui.structures.elements.NavigationElement
import kotlinx.html.*

val NavigationElementRenderer = renderer<NavigationElement> { element ->
    nav {
        ul {
            element.items.forEach { navigationItem ->
                li {
                    navigationItem.before?.run {
                        +"$this "
                    }
                    a(href = navigationItem.url) { +navigationItem.text }
                }
            }
        }
    }
}
