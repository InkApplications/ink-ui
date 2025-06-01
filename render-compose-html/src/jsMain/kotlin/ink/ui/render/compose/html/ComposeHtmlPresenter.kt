package ink.ui.render.compose.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ink.ui.render.compose.html.renderer.*
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
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section
import org.jetbrains.compose.web.dom.Text

class ComposeHtmlPresenter(
    renderers: List<ElementRenderer> = emptyList(),
    private val missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
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
        DividerRenderer,
        FormattedTextRenderer,
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
    internal fun renderElement(element: UiElement)
    {
        when (val result = uiRenderer.render(element, uiRenderer)) {
            RenderResult.Skipped -> when (missingRendererBehavior) {
                is MissingRendererBehavior.Panic -> throw MissingRendererException(element)
                is MissingRendererBehavior.Placeholder -> {
                    missingRendererBehavior.log(element)
                    Div(
                        attrs = {
                            classes("error-box")
                        },
                    ) {
                        Text("{{ ${element::class.simpleName} }}")
                    }
                }
                is MissingRendererBehavior.Ignore -> {
                    missingRendererBehavior.log(element)
                }
            }
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
