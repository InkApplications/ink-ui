package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.breadcrumbs

val Breadcrumbs = ElementList.build(groupingStyle = Items) {
    breadcrumbs {
        link("Home", "https://asdf.com")
        text("Breadcrumbs")
    }
}
