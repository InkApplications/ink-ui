package ink.ui.render.compose.html

import androidx.compose.runtime.Composable
import ink.ui.render.compose.html.renderer.*
import ink.ui.render.compose.html.renderer.CompositeElementRenderer
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.Positioning
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.*
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
            is ScrollingListLayout -> Section(
                attrs = {
                    classes("item-list")
                }
            ) {
                uiLayout.items.forEach {
                    Div {
                        renderElement(it)
                    }
                }
            }
        }
    }

    @Composable
    fun renderElement(element: UiElement) {
        when (uiRenderer.render(element, uiRenderer)) {
            RenderResult.NotRendered -> throw IllegalArgumentException("No renderer registered for ${element::class.simpleName}")
            RenderResult.Rendered -> {}
        }
    }
}
