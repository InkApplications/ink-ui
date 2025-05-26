package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.BreadcrumbRenderer
import ink.ui.render.statichtml.renderer.CodeBlockRenderer
import ink.ui.render.statichtml.renderer.CompositeElementRenderer
import ink.ui.render.statichtml.renderer.DividerRenderer
import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.render.statichtml.renderer.FormattedTextRenderer
import ink.ui.render.statichtml.renderer.IconRenderer
import ink.ui.render.statichtml.renderer.LinkButtonRenderer
import ink.ui.render.statichtml.renderer.LinkNavigationRenderer
import ink.ui.render.statichtml.renderer.ListRenderer
import ink.ui.render.statichtml.renderer.StatusRenderer
import ink.ui.render.statichtml.renderer.TextListRenderer
import ink.ui.render.statichtml.renderer.TextRenderer
import ink.ui.render.statichtml.renderer.renderWith
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.layouts.CenteredElementLayout
import ink.ui.structures.layouts.EmptyLayout
import ink.ui.structures.layouts.FixedGridLayout
import ink.ui.structures.layouts.PageLayout
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.html.TagConsumer
import kotlinx.html.div
import kotlinx.html.section

/**
 * Renders chunks of HTML, but not a full page.
 */
class HtmlPartialPresenter(
    resourceBaseUrl: String,
    customRenderers: Array<ElementRenderer> = emptyArray(),
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
        when (uiLayout) {
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
