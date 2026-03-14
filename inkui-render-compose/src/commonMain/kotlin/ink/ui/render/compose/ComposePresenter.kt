package ink.ui.render.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ink.ui.render.compose.renderer.*
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.EmptyElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.flow.MutableStateFlow

class ComposePresenter(
    renderers: List<ElementRenderer> = emptyList(),
    missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    private val builtInRenderers = listOf(
        ActivityRenderer,
        ButtonRenderer,
        TextRenderer,
        StatusIndicatorRenderer,
        EmptyRenderer,
        ListRenderer,
        CheckboxRenderer,
        MenuRowRenderer,
        SpinnerRenderer,
        IconRenderer,
        DividerRenderer,
        TextListRenderer,
        FormattedTextRenderer(),
    )
    internal val uiRenderer = CompositeElementRenderer(renderers + builtInRenderers, missingRendererBehavior)
    private val currentElement = MutableStateFlow<UiElement>(EmptyElement)

    @Composable
    fun bind(theme: ComposeRenderTheme)
    {
        val state = currentElement.collectAsState()

        uiRenderer.render(state.value, theme, uiRenderer)
    }

    override fun presentElement(element: UiElement)
    {
        currentElement.value = element
    }
}

@Composable
fun ComposePresenter.bindAndPresent(
    theme: ComposeRenderTheme,
    element: UiElement,
) {
    bind(theme)
    presentElement(element)
}
