package ink.ui.render.compose.html

import androidx.compose.runtime.Composable
import ink.ui.render.compose.html.renderer.*
import ink.ui.render.compose.html.renderer.CompositeElementRenderer
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.GroupingStyle.*
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.*
import ink.ui.structures.render.RenderResult
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Section

class HtmlComposeRenderer(
    renderers: List<ElementRenderer> = emptyList(),
) {
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

    @Composable
    fun render(uiLayout: UiLayout) {
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
                                        justifyContent(JustifyContent.Start)
                                    }
                                    Positioning.Center -> {
                                        justifyContent(JustifyContent.Center)
                                    }
                                }
                                when (it.verticalPositioning) {
                                    Positioning.Start -> {
                                        alignItems(AlignItems.Start)
                                    }
                                    Positioning.Center -> {
                                        alignItems(AlignItems.Center)
                                    }
                                }
                            }
                        }
                    ) {
                        renderElement(it.body)
                    }
                }
            }
            is PageLayout -> {
                Section {
                    renderElement(uiLayout.body)
                }
            }
            is ScrollingListLayout -> when (uiLayout.groupingStyle) {
                Unified -> renderElement(ElementList(uiLayout.items, groupingStyle = Unified))
                Items -> renderElement(ElementList(uiLayout.items, groupingStyle = Items))
                Sections -> uiLayout.items.forEach {
                    Section {
                        renderElement(it)
                    }
                }
            }
        }
    }

    @Composable
    fun renderElement(element: UiElement) {
        when (val result = uiRenderer.render(element, uiRenderer)) {
            RenderResult.Skipped -> throw IllegalArgumentException("No renderer registered for ${element::class.simpleName}")
            is RenderResult.Failed -> throw result.exception
            RenderResult.Rendered -> {}
        }
    }
}
