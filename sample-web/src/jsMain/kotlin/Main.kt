import example.Examples
import ink.ui.render.compose.html.ComposeHtmlPresenter
import org.jetbrains.compose.web.renderComposable

fun main() {
    val presenter = ComposeHtmlPresenter()
    renderComposable(rootElementId = "compose-root") {
        presenter.bind()
    }
    presenter.presentLayout(Examples.Defaults.layout)
}
