package ink.ui.render.statichtml

import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import kotlinx.html.TagConsumer
import kotlinx.html.dom.createHTMLDocument
import java.io.File
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate

@KotlinScript(
    fileExtension = "inkui.kts",
    compilationConfiguration = InkUiConfig::class
)
@Suppress("unused")
abstract class InkUiScript(
    private val scriptFile: File,
): InkUiBuilder {
    private var pageHeaders: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var bodies: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var styles: MutableList<String> = mutableListOf()
    private val document = createHTMLDocument()
    var title: String? = null
    var sectioned: Boolean = false
    var contentBreak: Boolean = false
    final override var resourceBaseUrl: String = "https://ui.inkapplications.com/res"
        set(value) {
            field = value
            renderer = HtmlRenderer(value)
        }
    private var renderer = HtmlRenderer(resourceBaseUrl)

    override fun addPageHeader(element: UiElement) {
        pageHeaders.add(renderer.renderElement(element))
    }

    override fun addPageHeader(block: TagConsumer<*>.() -> Unit) {
        pageHeaders.add(block)
    }

    override fun addBody(block: TagConsumer<*>.() -> Unit) {
        bodies.add(block)
    }

    override fun addBody(layout: UiLayout) {
        bodies.add(renderer.renderLayout(layout))
    }

    override fun addStyle(stylesheet: String) {
        styles += stylesheet
    }

    override fun include(file: String) {
        evalPartial(File(scriptFile.parentFile, file), this).valueOrThrow()
    }

    private fun getStyles(): List<String> {
        return listOf(
            "$resourceBaseUrl/css/main-2.0.css",
        ) + styles
    }

    internal fun getHtml(): String {
        return renderer.renderDocument(
            pageTitle = title ?: scriptFile.name.replace(Regex("\\.inkui\\.kts$", RegexOption.IGNORE_CASE), ""),
            pageHeaders = pageHeaders,
            bodies = bodies,
            stylesheets = getStyles(),
            sectioned = sectioned,
            contentBreak = contentBreak,
        )
    }

    companion object {
        internal fun evalFile(scriptFile: File): ResultWithDiagnostics<EvaluationResult> {
            val source = scriptFile.toScriptSource()
            val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<InkUiScript>()
            val evaluationConfiguration =  ScriptEvaluationConfiguration {
                constructorArgs(scriptFile)
            }
            return BasicJvmScriptingHost().eval(
                script = source,
                compilationConfiguration = compilationConfiguration,
                evaluationConfiguration = evaluationConfiguration
            )
        }

        private fun evalPartial(scriptFile: File, parent: InkUiScript): ResultWithDiagnostics<EvaluationResult> {
            val source = scriptFile.toScriptSource()
            val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<PartialScript>()
            val evaluationConfiguration =  ScriptEvaluationConfiguration {
                constructorArgs(parent)
            }
            return BasicJvmScriptingHost().eval(
                script = source,
                compilationConfiguration = compilationConfiguration,
                evaluationConfiguration = evaluationConfiguration
            )
        }
    }
}
