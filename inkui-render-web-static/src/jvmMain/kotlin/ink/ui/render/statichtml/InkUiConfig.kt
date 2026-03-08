package ink.ui.render.statichtml

import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

object InkUiConfig: ScriptCompilationConfiguration({
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
        defaultImports("kotlinx.html.*")
        defaultImports("ink.ui.structures.*")
        defaultImports("ink.ui.structures.layouts.*")
        defaultImports("ink.ui.structures.elements.*")
        defaultImports("ink.ui.render.web.elements.*")
        defaultImports("ink.ui.render.statichtml.renderer.ElementRenderer")
        defaultImports("ink.ui.render.statichtml.renderer.renderer")
        defaultImports("ink.ui.structures.render.RenderResult")
        defaultImports("ink.ui.render.web.elements.CodeBlock.Language")
        defaultImports("ink.ui.render.web.elements.LinkNavigation.NavigationItem")
    }
})
