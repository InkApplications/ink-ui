package ink.ui.render.compose.html

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import ink.ui.render.compose.html.renderer.*
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.layouts.*
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section

class ComposeHtmlPresenter(
    renderers: List<ElementRenderer> = emptyList(),
    missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
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
        TextListRenderer,
    )
    internal val compositeRenderer = CompositeElementRenderer(renderers + buildInRenderers, missingRendererBehavior)
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
                compositeRenderer.render(uiLayout.body, compositeRenderer)
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
                        compositeRenderer.render(it.body, compositeRenderer)
                    }
                }
            }
            is ScrollingListLayout -> when (uiLayout.groupingStyle) {
                GroupingStyle.Unified -> compositeRenderer.render(
                    ElementList(
                        uiLayout.items,
                        groupingStyle = GroupingStyle.Unified
                    ),
                    compositeRenderer
                )
                GroupingStyle.Items -> compositeRenderer.render(ElementList(uiLayout.items, groupingStyle = GroupingStyle.Items), compositeRenderer)
                GroupingStyle.Sections -> uiLayout.items.forEach {
                    Section {
                        compositeRenderer.render(it, compositeRenderer)
                    }
                }
                GroupingStyle.Inline -> uiLayout.items.forEach {
                    compositeRenderer.render(it, compositeRenderer)
                }
            }
            is EmptyLayout -> {}
            is PageLayout -> {
                Section {
                    compositeRenderer.render(uiLayout.body, compositeRenderer)
                }
            }
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
