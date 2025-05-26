package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.*
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.*
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.MissingRendererBehavior.Panic.MissingRendererException
import ink.ui.structures.render.Presenter
import ink.ui.structures.render.RenderResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.html.*

/**
 * Renders chunks of HTML, but not a full page.
 */
class HtmlPartialPresenter(
    resourceBaseUrl: String,
    customRenderers: Array<ElementRenderer> = emptyArray(),
    private val missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    private val iconUrl = "$resourceBaseUrl/svg"
    private val builtInRenderers = listOf(
        *customRenderers,
        ListRenderer,
        TextRenderer,
        CodeBlockRenderer,
        DividerRenderer,
        FormattedTextRenderer,
        BreadcrumbRenderer,
        LinkNavigationRenderer,
        TextListRenderer,
        StatusRenderer(iconUrl),
        IconRenderer(iconUrl),
        LinkButtonRenderer(iconUrl)
    )
    private val renderer = CompositeElementRenderer(builtInRenderers)
    private val currentLayout = MutableStateFlow<UiLayout>(EmptyLayout)
    val isEmpty: Boolean get() = currentLayout.value is EmptyLayout

    override fun presentLayout(layout: UiLayout)
    {
        currentLayout.value = layout
    }

    fun getPartialRenderer(): TagConsumer<*>.() -> Unit = render(currentLayout.value)

    internal fun render(uiLayout: UiLayout): TagConsumer<*>.() -> Unit = {
        val result = when (uiLayout) {
            is CenteredElementLayout -> section("element-center") {
                renderElement(
                    element = uiLayout.body,
                    consumer = consumer,
                )
            }
            is FixedGridLayout -> section("fixed-grid") {
                attributes["style"] = "grid-template-columns: ${uiLayout.gridTemplateColumns};"

                uiLayout.items.forEach { item ->
                    div {
                        val horizontalPosition = when (item.horizontalPositioning) {
                            Positioning.Start -> "start"
                            Positioning.Center -> "center"
                        }
                        val verticalPosition = when (item.verticalPositioning) {
                            Positioning.Start -> "start"
                            Positioning.Center -> "center"
                        }
                        attributes["style"] = "grid-column: span ${item.span}; align-items: $verticalPosition; justify-content: $horizontalPosition;"
                        renderElement(
                            element = item.body,
                            consumer = consumer,
                        )
                    }
                }
            }
            is ScrollingListLayout -> when (uiLayout.groupingStyle) {
                GroupingStyle.Unified -> section {
                    renderElement(
                        element = ElementList(uiLayout.items, groupingStyle = GroupingStyle.Unified),
                        consumer = consumer,
                    )
                }
                GroupingStyle.Items -> section {
                    renderElement(
                        element = ElementList(uiLayout.items, groupingStyle = GroupingStyle.Items),
                        consumer = consumer,
                    )
                }
                GroupingStyle.Sections -> uiLayout.items.forEach { item ->
                    section {
                        renderElement(
                            element = item,
                            consumer = consumer,
                        )
                    }
                }
                GroupingStyle.Inline -> uiLayout.items.forEach { item ->
                    renderElement(
                        element = item,
                        consumer = this,
                    )
                }
            }
            is EmptyLayout -> {}
            is PageLayout -> section {
                renderElement(
                    element = uiLayout.body,
                    consumer = consumer,
                )
            }
        }
    }

    private fun renderElement(
        element: UiElement,
        consumer: TagConsumer<*>,
    ) {
        val renderResult = renderWith(
            element = element,
            consumer = consumer,
            renderer = renderer,
        )
        when (renderResult) {
            is RenderResult.Skipped -> {
                when (missingRendererBehavior) {
                    is MissingRendererBehavior.Placeholder -> {
                        missingRendererBehavior.log(element)
                        consumer.div("error-box") {
                            +"{{ ${element::class.simpleName} }}"
                        }
                    }
                    is MissingRendererBehavior.Ignore -> {
                        missingRendererBehavior.log(element)
                    }
                    is MissingRendererBehavior.Panic -> throw MissingRendererException(element)
                }
            }
            is RenderResult.Failed -> throw renderResult.exception
            is RenderResult.Rendered -> {}
        }
    }
}
