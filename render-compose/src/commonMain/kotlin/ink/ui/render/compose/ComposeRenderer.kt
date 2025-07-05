package ink.ui.render.compose

import androidx.compose.runtime.Composable
import ink.ui.render.compose.renderer.ElementRenderer
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout

/**
 * Renders UI Layouts and elements using Compose.
 */
@Deprecated("Use Presenter interface instead")
class ComposeRenderer(
    private val theme: ComposeRenderTheme,
    renderers: List<ElementRenderer> = emptyList(),
) {
    private val presenter = ComposePresenter(renderers)

    @Deprecated("Use Presenter interface instead")
    @Composable
    fun render(uiLayout: UiLayout) {
        presenter.render(uiLayout, theme)
    }

    @Composable
    @Deprecated("Use presenter interface instead")
    fun renderElement(element: UiElement) {
        presenter.uiRenderer.render(element, theme, presenter.uiRenderer)
    }
}
