package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
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
}

