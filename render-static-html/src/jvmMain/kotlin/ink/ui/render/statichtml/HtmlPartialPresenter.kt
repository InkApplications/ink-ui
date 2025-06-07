package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.*
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.layouts.*
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
    private val renderer = CompositeElementRenderer(builtInRenderers, missingRendererBehavior)
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
                renderWith(
                    element = uiLayout.body,
                    consumer = consumer,
                    renderer = renderer,
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
                        renderWith(
                            element = item.body,
                            consumer = consumer,
                            renderer = renderer,
                        )
                    }
                }
            }
            is ScrollingListLayout -> when (uiLayout.groupingStyle) {
                GroupingStyle.Unified -> section {
                    renderWith(
                        element = ElementList(uiLayout.items, groupingStyle = GroupingStyle.Unified),
                        consumer = consumer,
                        renderer = renderer,
                    )
                }
                GroupingStyle.Items -> section {
                    renderWith(
                        element = ElementList(uiLayout.items, groupingStyle = GroupingStyle.Items),
                        consumer = consumer,
                        renderer = renderer,
                    )
                }
                GroupingStyle.Sections -> uiLayout.items.forEach { item ->
                    section {
                        renderWith(
                            element = item,
                            consumer = consumer,
                            renderer = renderer,
                        )
                    }
                }
                GroupingStyle.Inline -> uiLayout.items.forEach { item ->
                    renderWith(
                        element = item,
                        consumer = this,
                        renderer = renderer,
                    )
                }
            }
            is EmptyLayout -> {}
            is PageLayout -> section {
                renderWith(
                    element = uiLayout.body,
                    consumer = consumer,
                    renderer = renderer,
                )
            }
        }
    }
}
