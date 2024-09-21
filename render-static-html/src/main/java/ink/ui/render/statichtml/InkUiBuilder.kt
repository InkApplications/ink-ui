package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import kotlinx.html.TagConsumer

interface InkUiBuilder {
    val resourceBaseUrl: String
    fun addPageHeader(element: UiElement)
    fun addPageHeader(block: TagConsumer<*>.() -> Unit)
    fun addBody(block: TagConsumer<*>.() -> Unit)
    fun addBody(layout: UiLayout)
    fun addStyle(stylesheet: String)
    fun include(file: String)
    fun elementRenderer(elementRenderer: ElementRenderer)
    fun addPageFooter(element: UiElement)
    fun addPageFooter(block: TagConsumer<*>.() -> Unit)
}
