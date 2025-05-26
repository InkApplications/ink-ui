package ink.ui.render.compose.html

import androidx.compose.runtime.Composable
import ink.ui.render.compose.html.renderer.ElementRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout

@Deprecated("Use ComposeHtmlPresenter instead")
class HtmlComposeRenderer(
    renderers: List<ElementRenderer> = emptyList(),
) {
    private val presenter = ComposeHtmlPresenter(renderers)

    @Deprecated("Use ComposeHtmlPresenter instead")
    @Composable
    fun render(uiLayout: UiLayout)
    {
        presenter.render(uiLayout)
    }

    @Deprecated("Use ComposeHtmlPresenter instead")
    @Composable
    fun renderElement(element: UiElement) {
        presenter.renderElement(element)
    }
}
