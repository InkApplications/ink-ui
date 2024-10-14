package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import kotlinx.html.*
import kotlinx.html.dom.createHTMLDocument
import java.io.File
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

@KotlinScript(
    fileExtension = "inkui.kts",
    compilationConfiguration = InkUiConfig::class
)
@Suppress("unused")
abstract class InkUiScript(
    val scriptFile: File,
    customRenderers: Array<ElementRenderer>,
    private val customImports: Array<String>,
): InkUiBuilder {
    private val pageProperties: PageProperties = PageProperties(
        title = scriptFile.name.replace(Regex("\\.inkui\\.kts$", RegexOption.IGNORE_CASE), "")
    )
    private var heads: MutableList<HEAD.() -> Unit> = mutableListOf()
    private var pageHeaders: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var pageFooters: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var bodies: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var styles: MutableList<String> = mutableListOf()
    private var scripts: MutableList<String> = mutableListOf()
    private val document = createHTMLDocument()
    final override var title: String by pageProperties::title
    final override var sectioned: Boolean by pageProperties::sectioned
    final override var contentBreak: Boolean by pageProperties::contentBreak
    final override var inkFooter: Boolean by pageProperties::inkFooter
    final override var codeBlocks: Boolean = false
    final override var resourceBaseUrl: String = "https://ui.inkapplications.com/res"
        set(value) {
            field = value
            renderer = renderer.withResourceBaseUrl(value)
        }
    private var renderer = HtmlRenderer(resourceBaseUrl, customRenderers)

    override fun addHead(block: HEAD.() -> Unit) {
        heads.add(block)
    }

    override fun addPageHeader(element: UiElement) {
        pageHeaders.add(renderer.renderElement(element))
    }

    override fun addPageHeader(block: TagConsumer<*>.() -> Unit) {
        pageHeaders.add(block)
    }

    override fun addPageFooter(element: UiElement) {
        pageFooters.add(renderer.renderElement(element))
    }

    override fun addPageFooter(block: TagConsumer<*>.() -> Unit) {
        pageFooters.add(block)
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

    override fun elementRenderer(elementRenderer: ElementRenderer) {
        renderer = renderer.withRenderer(elementRenderer)
    }

    override fun include(file: String) {
        evalPartial(
            scriptFile = File(scriptFile.parentFile, file),
            parent = this,
            customImports = customImports,
        ).valueOrThrow()
    }

    override fun resource(name: String): String {
        if (resourceBaseUrl.isBlank()) {
            return name
        }
        return "$resourceBaseUrl/$name"
    }

    private fun getStyles(): List<String> {
        return listOf(
            resource("css/main-2.0.css"),
        ) + styles
    }

    fun getHtml(): String {
        return renderer.renderDocument(
            pageProperties = pageProperties,
            pageHeaders = pageHeaders,
            pageFooters = pageFooters,
            bodies = bodies,
            stylesheets = getStyles(),
            scripts = listOfNotNull(
                *scripts.toTypedArray(),
                "$resourceBaseUrl/js/highlight.pack.js".takeIf { codeBlocks },
            ),
            jsInit = "hljs.initHighlightingOnLoad();".takeIf { codeBlocks },
            heads = heads,
        )
    }

    companion object {
        fun evalFile(
            scriptFile: File,
            customRenderers: Array<ElementRenderer> = emptyArray(),
            customImports: Array<String> = emptyArray(),
        ): ResultWithDiagnostics<EvaluationResult> {
            val source = scriptFile.toScriptSource()
            val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<InkUiScript> {
                defaultImports(*customImports)
            }
            val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<InkUiScript> {
                constructorArgs(scriptFile, customRenderers, customImports)
            }
            return BasicJvmScriptingHost().eval(
                script = source,
                compilationConfiguration = compilationConfiguration,
                evaluationConfiguration = evaluationConfiguration
            )
        }

        private fun evalPartial(
            scriptFile: File,
            parent: InkUiScript,
            customImports: Array<String>,
        ): ResultWithDiagnostics<EvaluationResult> {
            val source = scriptFile.toScriptSource()
            val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<PartialScript> {
                defaultImports(*customImports)
            }
            val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<PartialScript> {
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
