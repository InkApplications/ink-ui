import ink.ui.render.compose.html.ComposeHtmlPresenter
import ink.ui.render.compose.html.bindAndPresent
import ink.ui.sample.web.SampleScreen
import org.jetbrains.compose.web.renderComposable

fun main() {
    val renderer = ComposeHtmlPresenter()
    renderComposable(rootElementId = "compose-root") {
        renderer.bindAndPresent(SampleScreen)
    }
}
