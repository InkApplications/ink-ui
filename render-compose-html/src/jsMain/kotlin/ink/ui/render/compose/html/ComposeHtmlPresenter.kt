package ink.ui.render.compose.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ink.ui.render.compose.html.renderer.ActivityRenderer
import ink.ui.render.compose.html.renderer.ButtonRenderer
import ink.ui.render.compose.html.renderer.CheckBoxRenderer
import ink.ui.render.compose.html.renderer.CompositeElementRenderer
import ink.ui.render.compose.html.renderer.ElementRenderer
import ink.ui.render.compose.html.renderer.EmptyRenderer
import ink.ui.render.compose.html.renderer.IconRenderer
import ink.ui.render.compose.html.renderer.LinkButtonRenderer
import ink.ui.render.compose.html.renderer.ListRenderer
import ink.ui.render.compose.html.renderer.MenuRowRenderer
import ink.ui.render.compose.html.renderer.SpinnerRenderer
import ink.ui.render.compose.html.renderer.StatusRenderer
import ink.ui.render.compose.html.renderer.TextRenderer
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.CenteredElementLayout
import ink.ui.structures.layouts.EmptyLayout
import ink.ui.structures.layouts.FixedGridLayout
import ink.ui.structures.layouts.PageLayout
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
import ink.ui.structures.render.Presenter
import ink.ui.structures.render.RenderResult
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.gridColumn
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section

class ComposeHtmlPresenter(
    renderers: List<ElementRenderer> = emptyList(),
): Presenter {
    private val buildInRenderers: List<ElementRenderer> = listOf(
        ListRenderer,
        ActivityRenderer,
        TextRenderer,
        ButtonRenderer,
        LinkButtonRenderer,
        StatusRenderer,
        EmptyRenderer,
        SpinnerRenderer,
        IconRenderer,
        CheckBoxRenderer,
        MenuRowRenderer,
    )
    private val uiRenderer = CompositeElementRenderer(renderers + buildInRenderers)
    private val current = MutableStateFlow<UiLayout>(EmptyLayout)

    override fun presentLayout(layout: UiLayout) {
        current.value = layout
    }

    @Composable
    fun bind() {
        val state = current.collectAsState()

        render(state.value)
    }

    @Composable
    internal fun render(uiLayout: UiLayout) {
        when (uiLayout) {
            is CenteredElementLayout -> Section(
                attrs = {
                    classes("element-center")
                }
            ) {
                renderElement(uiLayout.body)
            }
            is FixedGridLayout -> Section(
                attrs = {
                    classes("fixed-grid")
                    style {
                        gridTemplateColumns(uiLayout.gridTemplateColumns)
                    }
                }
            ) {
                uiLayout.items.forEach {
                    Div(
                        attrs = {
                            style {
                                gridColumn("span ${it.span}")
                                when (it.horizontalPositioning) {
                                    Positioning.Start -> {
                                        justifyContent(JustifyContent.Companion.Start)
                                    }

                                    Positioning.Center -> {
                                        justifyContent(JustifyContent.Companion.Center)
                                    }
                                }
                                when (it.verticalPositioning) {
                                    Positioning.Start -> {
                                        alignItems(AlignItems.Companion.Start)
                                    }

                                    Positioning.Center -> {
                                        alignItems(AlignItems.Companion.Center)
                                    }
                                }
                            }
                        }
                    ) {
                        renderElement(it.body)
                    }
                }
            }
            is ScrollingListLayout -> when (uiLayout.groupingStyle) {
                GroupingStyle.Unified -> renderElement(
                    ElementList(
                        uiLayout.items,
                        groupingStyle = GroupingStyle.Unified
                    )
                )
                GroupingStyle.Items -> renderElement(ElementList(uiLayout.items, groupingStyle = GroupingStyle.Items))
                GroupingStyle.Sections -> uiLayout.items.forEach {
                    Section {
                        renderElement(it)
                    }
                }
                GroupingStyle.Inline -> uiLayout.items.forEach {
                    renderElement(it)
                }
            }
            is EmptyLayout -> {}
            is PageLayout -> {
                Section {
                    renderElement(uiLayout.body)
                }
            }
        }
    }

    @Composable
    internal fun renderElement(element: UiElement) {
        when (val result = uiRenderer.render(element, uiRenderer)) {
            RenderResult.Skipped -> throw IllegalArgumentException("No renderer registered for ${element::class.simpleName}")
            is RenderResult.Failed -> throw result.exception
            RenderResult.Rendered -> {}
        }
    }
}

@Composable
fun ComposeHtmlPresenter.bindAndPresent(
    layout: UiLayout
) {
    bind()
    presentLayout(layout)
}
