package ink.ui.render.statichtml

import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.*
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import java.io.File
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvm.util.isError
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

@KotlinScript(
    fileExtension = "inkui.kts",
    compilationConfiguration = InkUiConfig::class
)
@Suppress("unused")
abstract class InkUiScript {
    private var pageHeaders: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var bodies: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var styles: MutableList<String> = mutableListOf()
    private val document = createHTMLDocument()
    private val renderer = HtmlRenderer()
    internal lateinit var fileName: String
    var title: String? = null
    var sectioned: Boolean = false
    var contentBreak: Boolean = false

    fun addPageHeader(element: UiElement) {
        pageHeaders.add(renderer.renderElement(element))
    }

    fun addPageHeader(block: TagConsumer<*>.() -> Unit) {
        pageHeaders.add(block)
    }

    fun addBody(block: TagConsumer<*>.() -> Unit) {
        bodies.add(block)
    }

    fun addBody(layout: UiLayout) {
        bodies.add(renderer.renderLayout(layout))
    }

    fun addStyle(stylesheet: String) {
        styles += stylesheet
    }

    fun useHostedStyles() {
        styles += "https://assets.inkapplications.com/css/main-v1.3.css"
    }

    internal fun getHtml(): String {
        return renderer.renderDocument(
            pageTitle = title ?: fileName,
            pageHeaders = pageHeaders,
            bodies = bodies,
            stylesheets = styles,
            sectioned = sectioned,
            contentBreak = contentBreak,
        )
    }


    companion object {
        internal fun evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
            val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<InkUiScript>()
            return BasicJvmScriptingHost()
                .eval(scriptFile.toScriptSource(), compilationConfiguration, null)
                .apply {
                    if (!isError()) {
                        val script = (valueOrThrow().returnValue.scriptInstance as InkUiScript)
                        script.fileName = scriptFile.name.replace(Regex("\\.inkui\\.kts$", RegexOption.IGNORE_CASE), "")
                    }
                }
        }
    }
}
