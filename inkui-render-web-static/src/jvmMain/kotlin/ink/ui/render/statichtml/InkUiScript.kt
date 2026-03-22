package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.ElementContainer
import ink.ui.structures.GroupingStyle
import ink.ui.structures.MutableListElementContainer
import ink.ui.structures.elements.ElementList
import kotlinx.html.*
import kotlinx.html.dom.*
import java.io.File
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.*

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
    final override val page: PageProperties = PageProperties(
        title = scriptFile.name.replace(Regex("\\.inkui\\.kts$", RegexOption.IGNORE_CASE), "")
    )
    override val meta: PageProperties.Meta = page.meta
    private var heads: MutableList<HEAD.() -> Unit> = mutableListOf()
    private var styles: MutableList<String> = mutableListOf()
    private var scripts: MutableList<String> = mutableListOf()
    private val document = createHTMLDocument()
    final override var useCodeBlocks: Boolean = false
    final override var resourceBaseUrl: String = "https://ui.inkapplications.com/res"
        set(value) {
            field = value
            presenter = presenter.withResourceBaseUrl(value)
        }
    private var presenter = StaticHtmlDocumentPresenter(resourceBaseUrl, customRenderers)

    override fun head(builder: HEAD.() -> Unit)
    {
        heads.add(builder)
    }

    override fun header(builder: ElementContainer.() -> Unit)
    {
        MutableListElementContainer()
            .apply(builder)
            .build()
            .let {
                ElementList(
                    items = it,
                    groupingStyle = GroupingStyle.Inline,
                )
            }
            .also { presenter.presentHeader(it) }
    }

    override fun footer(builder: ElementContainer.() -> Unit)
    {
        MutableListElementContainer()
            .apply(builder)
            .build()
            .let {
                ElementList(
                    items = it,
                    groupingStyle = GroupingStyle.Inline,
                )
            }
            .also { presenter.presentFooter(it) }
    }

    override fun body(builder: ElementContainer.() -> Unit)
    {
        MutableListElementContainer()
            .apply(builder)
            .build()
            .let {
                ElementList(
                    items = it,
                    groupingStyle = GroupingStyle.Inline,
                )
            }
            .also { presenter.presentBody(it) }
    }

    override fun addStyle(stylesheet: String) {
        styles += stylesheet
    }

    override fun elementRenderer(elementRenderer: ElementRenderer)
    {
        presenter = presenter.withRenderer(elementRenderer)
    }

    override fun include(file: String)
    {
        evalPartial(
            scriptFile = File(scriptFile.parentFile, file),
            parent = this,
            customImports = customImports,
        ).valueOrThrow()
    }

    override fun resource(name: String): String
    {
        if (resourceBaseUrl.isBlank()) {
            return name
        }
        return "$resourceBaseUrl/$name"
    }

    private fun getStyles(): List<String>
    {
        return listOf(
            resource("css/inkui-2.0.css"),
        ) + styles
    }

    fun getHtml(): String
    {
        return presenter.renderDocument(
            pageProperties = page,
            stylesheets = getStyles(),
            scripts = listOfNotNull(
                *scripts.toTypedArray(),
                "$resourceBaseUrl/js/highlight.pack.js".takeIf { useCodeBlocks },
            ),
            jsInit = "hljs.initHighlightingOnLoad();".takeIf { useCodeBlocks },
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
