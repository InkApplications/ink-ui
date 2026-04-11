package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.*
import ink.ui.structures.elements.EmptyElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.html.*

/**
 * Renders chunks of HTML, but not a full page.
 */
class HtmlPartialPresenter(
    resourceBaseUrl: String,
    customRenderers: Array<ElementRenderer> = emptyArray(),
    missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    private val iconUrl = "$resourceBaseUrl/svg"
    private val builtInRenderers = listOf(
        *customRenderers,
        ListRenderer,
        ElementGridRenderer,
        TextRenderer,
        CodeBlockRenderer,
        DividerRenderer,
        FormattedTextRenderer,
        BreadcrumbRenderer,
        EmptyElementRenderer,
        NavigationElementRenderer,
        TextListRenderer,
        StatusRenderer(iconUrl),
        IconRenderer(iconUrl),
        ButtonRenderer(iconUrl),
    )
    private val renderer = CompositeElementRenderer(builtInRenderers, missingRendererBehavior)
    private val currentElement = MutableStateFlow<UiElement>(EmptyElement)
    val isEmpty: Boolean get() = currentElement.value is EmptyElement

    override fun presentElement(element: UiElement)
    {
        currentElement.value = element
    }

    fun getPartialRenderer(): TagConsumer<*>.() -> Unit = render(currentElement.value)

    internal fun render(element: UiElement): TagConsumer<*>.() -> Unit = {
        renderWith(
            element = element,
            consumer = this,
            renderer = renderer,
        )
    }
}
