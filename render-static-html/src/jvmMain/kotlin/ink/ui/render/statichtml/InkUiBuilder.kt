package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import kotlinx.html.*

interface InkUiBuilder
{
    var useCodeBlocks: Boolean
    var resourceBaseUrl: String
    val page: PageProperties
    val meta: PageProperties.Meta
    fun addHead(block: HEAD.() -> Unit)
    fun setHeader(layout: UiLayout)
    fun setBody(layout: UiLayout)
    fun addStyle(stylesheet: String)
    fun include(file: String)
    fun elementRenderer(elementRenderer: ElementRenderer)
    fun setFooter(layout: UiLayout)
    fun resource(name: String): String

    @Deprecated("Use setHeader(layout: UiLayout) instead")
    fun addPageHeader(element: UiElement)
    @Deprecated("Use setHeader(layout: UiLayout) instead")
    fun addPageHeader(block: TagConsumer<*>.() -> Unit)
    @Deprecated("Use setBody(element: UiElement) instead")
    fun addBody(block: TagConsumer<*>.() -> Unit)
    @Deprecated("Use setBody(element: UiElement) instead", replaceWith = ReplaceWith("setBody(layout)"))
    fun addBody(layout: UiLayout)
    @Deprecated("Use setFooter(layout: UiLayout) instead")
    fun addPageFooter(element: UiElement)
    @Deprecated("Use setFooter(layout: UiLayout) instead")
    fun addPageFooter(block: TagConsumer<*>.() -> Unit)
}

