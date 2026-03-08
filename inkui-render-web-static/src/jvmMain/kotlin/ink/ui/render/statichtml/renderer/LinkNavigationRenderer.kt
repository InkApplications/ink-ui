package ink.ui.render.statichtml.renderer

import ink.ui.render.web.elements.LinkNavigation
import kotlinx.html.*

val LinkNavigationRenderer = renderer<LinkNavigation> { element ->
    nav {
        ul {
            element.items.forEach { navigationItem ->
                li {
                    a(href = navigationItem.url) { +navigationItem.text }
                }
            }
        }
    }
}
