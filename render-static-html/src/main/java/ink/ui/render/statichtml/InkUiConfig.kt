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
    }
})
