package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import kotlinx.html.*
import kotlinx.html.dom.*

class StaticHtmlDocumentPresenter(
    private val resourceBaseUrl: String,
    private val customRenderers: Array<ElementRenderer> = emptyArray(),
    missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    internal val headerPresenter = HtmlPartialPresenter(resourceBaseUrl, customRenderers, missingRendererBehavior)
    internal val footerPresenter = HtmlPartialPresenter(resourceBaseUrl, customRenderers, missingRendererBehavior)
    internal val bodyPresenter = HtmlPartialPresenter(resourceBaseUrl, customRenderers, missingRendererBehavior)

    fun presentHeader(element: UiElement) = headerPresenter.presentElement(element)
    fun presentFooter(element: UiElement) = footerPresenter.presentElement(element)
    fun presentBody(element: UiElement) = bodyPresenter.presentElement(element)
    override fun presentElement(element: UiElement) = bodyPresenter.presentElement(element)

    fun renderDocument(
        pageProperties: PageProperties,
        heads: List<HEAD.() -> Unit>,
        stylesheets: List<String>,
        scripts: List<String>,
        jsInit: String?,
        // TODO: Remove these in 2.x
        pageHeaderCompat: List<TagConsumer<*>.() -> Unit> = emptyList(),
        pageFooterCompat: List<TagConsumer<*>.() -> Unit> = emptyList(),
        bodyCompat: List<TagConsumer<*>.() -> Unit> = emptyList(),
    ): String {
        return createHTMLDocument().html {
            attributes["lang"] = "en"
            head {
                stylesheets.forEach {
                    styleLink(it)
                }
                if (pageProperties.meta.deviceViewport) {
                    meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                }
                if (pageProperties.meta.robots != null) {
                    meta(name = "robots", content = pageProperties.meta.robots)
                }
                if (pageProperties.meta.author != null) {
                    meta(name = "author", content = pageProperties.meta.author)
                }
                if (pageProperties.meta.keywords != null) {
                    meta(name = "keywords", content = pageProperties.meta.keywords)
                }
                if (pageProperties.meta.description != null) {
                    meta(name = "description", content = pageProperties.meta.description)
                }

                heads.forEach { it() }

                scripts.forEach {
                    script(src = it) {}
                }

                title { +pageProperties.title }
            }
            val bodyClasses = listOfNotNull(
                when {
                    pageProperties.contentBreak -> "content-break"
                    else -> null
                },
                "anchored-footer".takeIf { pageProperties.inkFooter },
            ).joinToString(" ")
            body(classes = bodyClasses) {
                if (!headerPresenter.isEmpty || pageHeaderCompat.isNotEmpty()) {
                    header {
                        headerPresenter.getPartialRenderer().invoke(consumer)
                        pageHeaderCompat.forEach { it(consumer) }
                    }
                }
                bodyPresenter.getPartialRenderer().invoke(consumer)
                bodyCompat.forEach { it(consumer) }
                if (!footerPresenter.isEmpty || pageFooterCompat.isNotEmpty()) {
                    footer {
                        footerPresenter.getPartialRenderer().invoke(consumer)
                        pageFooterCompat.forEach { it(consumer) }
                    }
                }
                if (pageProperties.inkFooter) {
                    val classes = listOfNotNull(
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
        }.serialize(prettyPrint = false)
    }

    fun withResourceBaseUrl(url: String): StaticHtmlDocumentPresenter
    {
        return StaticHtmlDocumentPresenter(url, customRenderers)
    }

    fun withRenderer(renderer: ElementRenderer): StaticHtmlDocumentPresenter
    {
        return StaticHtmlDocumentPresenter(resourceBaseUrl, arrayOf(renderer, *customRenderers))
    }
}
