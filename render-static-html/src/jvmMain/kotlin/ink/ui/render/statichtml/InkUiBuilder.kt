package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import kotlinx.html.*

interface InkUiBuilder {
    var codeBlocks: Boolean
    var contentBreak: Boolean
    var deviceViewport: Boolean
    var inkFooter: Boolean
    var resourceBaseUrl: String
    var sectioned: Boolean
    var title: String
    var robots: String?
    fun addHead(block: HEAD.() -> Unit)
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

