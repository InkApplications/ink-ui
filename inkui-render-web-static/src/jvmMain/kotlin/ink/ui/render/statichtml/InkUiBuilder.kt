package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.ElementContainer
import kotlinx.html.*

interface InkUiBuilder
{
    var useCodeBlocks: Boolean
    var resourceBaseUrl: String
    val page: PageProperties
    val meta: PageProperties.Meta
    fun head(builder: HEAD.() -> Unit)
    fun header(builder: ElementContainer.() -> Unit)
    fun body(builder: ElementContainer.() -> Unit)
    fun addStyle(stylesheet: String)
    fun include(file: String)
    fun elementRenderer(elementRenderer: ElementRenderer)
    fun footer(builder: ElementContainer.() -> Unit)
    fun resource(name: String): String
}

