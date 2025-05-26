package ink.ui.render.statichtml

import ink.ui.render.statichtml.renderer.ElementRenderer
import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.ScrollingListLayout
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
    final override val page: PageProperties = PageProperties(
        title = scriptFile.name.replace(Regex("\\.inkui\\.kts$", RegexOption.IGNORE_CASE), "")
    )
    override val meta: PageProperties.Meta = page.meta
    private var heads: MutableList<HEAD.() -> Unit> = mutableListOf()
    // TODO: These compat fields are just here to support the cumulative rendering API, remove in 2.x
    private var pageHeadersCompat: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var pageFootersCompat: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
    private var bodiesCompat: MutableList<TagConsumer<*>.() -> Unit> = mutableListOf()
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

    override fun addHead(block: HEAD.() -> Unit)
    {
        heads.add(block)
    }

    override fun setHeader(layout: UiLayout)
    {
        presenter.presentHeader(layout)
    }

    @Deprecated("Use setHeader(layout: UiLayout) instead")
    override fun addPageHeader(element: UiElement)
    {
        pageHeadersCompat.add(
            presenter.headerPresenter.render(
                uiLayout = ScrollingListLayout(element, groupingStyle = GroupingStyle.Inline)
            )
        )
    }

    @Deprecated("Use setHeader(layout: UiLayout) instead")
    override fun addPageHeader(block: TagConsumer<*>.() -> Unit)
    {
        pageHeadersCompat.add(block)
    }

    override fun setFooter(layout: UiLayout)
    {
        presenter.presentFooter(layout)
    }

    override fun setBody(layout: UiLayout) {
        presenter.presentBody(layout)
    }

    @Deprecated("Use setFooter(layout: UiLayout) instead")
    override fun addPageFooter(element: UiElement)
    {
        pageFootersCompat.add(
            presenter.footerPresenter.render(
                uiLayout = ScrollingListLayout(element, groupingStyle = GroupingStyle.Inline)
            )
        )
    }

    @Deprecated("Use setFooter(layout: UiLayout) instead")
    override fun addPageFooter(block: TagConsumer<*>.() -> Unit)
    {
        pageFootersCompat.add(block)
    }

    @Deprecated("Use setBody(element: UiElement) instead")
    override fun addBody(block: TagConsumer<*>.() -> Unit)
    {
        bodiesCompat.add(block)
    }

    @Deprecated("Use setBody(element: UiElement) instead", replaceWith = ReplaceWith("setBody(layout)"))
    override fun addBody(layout: UiLayout) = setBody(layout)

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
            resource("css/inkui-1.0.css"),
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
            // TODO: Remove these in 2.x
            pageHeaderCompat = pageHeadersCompat,
            pageFooterCompat = pageFootersCompat,
            bodyCompat = bodiesCompat,
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
