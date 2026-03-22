package ink.ui.render.compose.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ink.ui.render.compose.html.renderer.*
import ink.ui.structures.elements.EmptyElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.flow.MutableStateFlow

class ComposeHtmlPresenter(
    renderers: List<ElementRenderer> = emptyList(),
    missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    private val buildInRenderers: List<ElementRenderer> = listOf(
        ListRenderer,
        ActivityRenderer,
        TextRenderer,
        ButtonRenderer,
        StatusRenderer,
        EmptyRenderer,
        SpinnerRenderer,
        IconRenderer,
        CheckBoxRenderer,
        DividerRenderer,
        FormattedTextRenderer,
        TextListRenderer,
    )
    internal val compositeRenderer = CompositeElementRenderer(renderers + buildInRenderers, missingRendererBehavior)
    private val current = MutableStateFlow<UiElement>(EmptyElement)

    override fun presentElement(element: UiElement)
    {
        current.value = element
    }

    @Composable
    fun bind() {
        val state = current.collectAsState()

        compositeRenderer.render(state.value, compositeRenderer)
    }
}

@Composable
fun ComposeHtmlPresenter.bindAndPresent(
    element: UiElement,
) {
    bind()
    presentElement(element)
}
