package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.*
import ink.ui.render.statichtml.renderer.CompositeElementRenderer
import ink.ui.render.statichtml.renderer.TextRenderer
import ink.ui.render.web.gridTemplateColumns
import ink.ui.structures.GroupingStyle.*
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
            is ScrollingListLayout -> when (uiLayout.groupingStyle) {
                Unified -> section {
                    renderWith(
                        element = ElementList(uiLayout.items, groupingStyle = Unified),
                        consumer = consumer,
                        renderer = renderer,
                    )
                }
                Items -> section {
                    renderWith(
                        element = ElementList(uiLayout.items, groupingStyle = Items),
                        consumer = consumer,
                        renderer = renderer,
                    )
                }
                Sections -> uiLayout.items.forEach { item ->
                    section {
                        renderWith(
                            element = item,
                            consumer = consumer,
                            renderer = renderer,
                        )
                    }
                }
            }
        }
    }

    fun renderDocument(
        pageProperties: PageProperties,
        heads: List<HEAD.() -> Unit>,
        pageHeaders: List<TagConsumer<*>.() -> Unit>,
        pageFooters: List<TagConsumer<*>.() -> Unit>,
        bodies: List<TagConsumer<*>.() -> Unit>,
        stylesheets: List<String>,
        scripts: List<String>,
        jsInit: String?,
    ): String {
        return createHTMLDocument().html {
            attributes["lang"] = "en"
            head {
                stylesheets.forEach {
                    styleLink(it)
                }
                if (pageProperties.deviceViewport) {
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                }
                heads.forEach { it() }

                scripts.forEach {
                    script(src = it) {}
                }

                title { +pageProperties.title }
            }
            val bodyClasses = listOfNotNull(
                when {
                    pageProperties.sectioned -> "sectioned"
                    pageProperties.contentBreak -> "content-break"
                    else -> null
                },
                "anchored-footer".takeIf { pageProperties.inkFooter },
            ).joinToString(" ")
            body(classes = bodyClasses) {
                if (pageHeaders.isNotEmpty()) {
                    header("content-break".takeIf { pageProperties.sectioned }) {
                        pageHeaders.forEach { it(consumer) }
                    }
                }
                bodies.forEach { it(consumer) }
                if (pageFooters.isNotEmpty()) {
                    footer("content-break".takeIf { pageProperties.sectioned }) {
                        pageFooters.forEach { it(consumer) }
                    }
                }
                if (pageProperties.inkFooter) {
                    val classes = listOfNotNull(
                        "content-break".takeIf { pageProperties.sectioned },
                        "ink"
                    ).joinToString(" ")
                    footer(classes) {
                        div("signature") {
                            a(href = "https://inkapplications.com") {
                                +"Ink Applications"
                            }
                        }
                    }
                }
                if (jsInit != null) {
                    script {
                        +jsInit
                    }
                }
            }
        }.serialize(prettyPrint = true)
    }

    fun withRenderer(renderer: ElementRenderer): HtmlRenderer {
        return HtmlRenderer(resourceBaseUrl, arrayOf(renderer, *customRenderers))
    }

    fun withResourceBaseUrl(url: String): HtmlRenderer {
        return HtmlRenderer(url, customRenderers)
    }
}
