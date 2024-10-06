package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import kotlinx.html.TagConsumer

interface InkUiBuilder {
    var title: String?
    var sectioned: Boolean
    var contentBreak: Boolean
    var inkFooter: Boolean
    var codeBlocks: Boolean
    var resourceBaseUrl: String
    fun addPageHeader(element: UiElement)
    fun addPageHeader(block: TagConsumer<*>.() -> Unit)
    fun addBody(block: TagConsumer<*>.() -> Unit)
    fun addBody(layout: UiLayout)
    fun addStyle(stylesheet: String)
    fun include(file: String)
    fun elementRenderer(elementRenderer: ElementRenderer)
    fun addPageFooter(element: UiElement)
    fun addPageFooter(block: TagConsumer<*>.() -> Unit)
    fun resource(name: String): String
}
