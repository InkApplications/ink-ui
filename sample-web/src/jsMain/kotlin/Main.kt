import ink.ui.render.compose.html.HtmlComposeRenderer
import ink.ui.sample.web.SampleScreen
import org.jetbrains.compose.web.renderComposable

fun main() {
    val renderer = HtmlComposeRenderer()
    renderComposable(rootElementId = "compose-root") {
        renderer.render(SampleScreen)
    }
}
