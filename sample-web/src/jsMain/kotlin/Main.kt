import example.Breadcrumbs
import example.Examples
import ink.ui.render.compose.html.ComposeHtmlPresenter
import ink.ui.render.compose.html.bindAndPresent
import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.ElementList
import org.jetbrains.compose.web.renderComposable

fun main() {
    val presenter = ComposeHtmlPresenter()
    renderComposable(rootElementId = "compose-root") {
        presenter.bindAndPresent(ElementList.build(groupingStyle = GroupingStyle.Inline) {
            appendElement(Breadcrumbs)
            appendElement(Examples.Defaults.groupedElements)
        })
    }
}
