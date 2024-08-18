package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.*
import ink.ui.render.statichtml.renderer.CompositeElementRenderer
import ink.ui.render.statichtml.renderer.TextRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.*
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import kotlinx.html.dom.serialize

class HtmlRenderer {
    private val builtInRenderers = listOf(
        ListRenderer,
        TextRenderer,
        StatusRenderer(null)
    )
    private val renderer = CompositeElementRenderer(builtInRenderers)

    fun renderElement(element: UiElement): TagConsumer<*>.() -> Unit = {
        renderWith(
            element = element,
            consumer = this,
            renderer = renderer,
        )
    }

    fun renderLayout(uiLayout: UiLayout): TagConsumer<*>.() -> Unit = {
        when (uiLayout) {
            is CenteredElementLayout -> TODO()
            is FixedGridLayout -> TODO()
            is PageLayout -> section {
                with(renderer) {
                    render(uiLayout.body, renderer)
                }
            }

            is ScrollingListLayout -> TODO()
        }
    }

    fun renderDocument(
        pageTitle: String,
        pageHeaders: List<TagConsumer<*>.() -> Unit>,
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
            }
        }.serialize(prettyPrint = true)
    }
}
