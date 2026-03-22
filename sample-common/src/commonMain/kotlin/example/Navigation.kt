package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.breadcrumbs
import ink.ui.structures.elements.navigation

val Breadcrumbs = ElementList.build(groupingStyle = Items) {
    breadcrumbs {
        link("Home", "https://asdf.com")
        text("Breadcrumbs")
    }
}

val Navigation = ElementList.build(groupingStyle = Items) {
    navigation {
        link("Asdf", "https://asdf.com")
        link("Example", "https://example.com")
    }
}
