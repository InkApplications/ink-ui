package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.*
import ink.ui.render.statichtml.renderer.CompositeElementRenderer
import ink.ui.render.statichtml.renderer.TextRenderer
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.Positioning
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.*
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize

class HtmlRenderer(
    private val resourceBaseUrl: String,
    private val customRenderers: Array<ElementRenderer> = emptyArray(),
) {
    private val iconUrl = "$resourceBaseUrl/svg"
    private val builtInRenderers = listOf(
        *customRenderers,
        ListRenderer,
        TextRenderer,
        DividerRenderer,
        FormattedTextRenderer,
        BreadcrumbRenderer,
        StatusRenderer(iconUrl),
        IconRenderer(iconUrl),
        LinkButtonRenderer(iconUrl)
    )
    private val renderer = CompositeElementRenderer(builtInRenderers)

    fun renderElement(element: UiElement): TagConsumer<*>.() -> Unit = {
        renderWith(
            element = element,
            consumer = this,
            renderer = renderer,
        )
    }

    fun renderLayout(uiLayout: UiLayout): TagConsumer<*>.() -> Unit = consumer@ {
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
            is PageLayout -> section {
                renderWith(
                    element = uiLayout.body,
                    consumer = consumer,
                    renderer = renderer,
                )
            }
            is ScrollingListLayout -> section {
                renderWith(
                    element = ElementList(uiLayout.items),
                    consumer = consumer,
                    renderer = renderer,
                )
            }
        }
    }

    fun renderDocument(
        pageTitle: String,
        pageHeaders: List<TagConsumer<*>.() -> Unit>,
        pageFooters: List<TagConsumer<*>.() -> Unit>,
        bodies: List<TagConsumer<*>.() -> Unit>,
        stylesheets: List<String>,
        sectioned: Boolean = false,
        contentBreak: Boolean = false
    ): String {
        return createHTMLDocument().html {
            attributes["lang"] = "en"
            head {
                stylesheets.forEach {
                    styleLink(it)
                }
                meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                title { +pageTitle }
            }
            body(classes = when {
                sectioned -> "sectioned"
                contentBreak -> "content-break"
                else -> ""
            }) {
                if (pageHeaders.isNotEmpty()) {
                    header("content-break".takeIf { sectioned }) {
                        pageHeaders.forEach { it(consumer) }
                    }
                }
                bodies.forEach { it(consumer) }
                if (pageFooters.isNotEmpty()) {
                    footer("content-break".takeIf { sectioned }) {
                        pageFooters.forEach { it(consumer) }
                    }
                }
            }
        }.serialize(prettyPrint = true)
    }

    fun withRenderer(renderer: ElementRenderer): HtmlRenderer {
        return HtmlRenderer(resourceBaseUrl, arrayOf(renderer, *customRenderers))
    }
}
