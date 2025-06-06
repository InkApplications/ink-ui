package ink.ui.cli

import ink.ui.render.statichtml.InkUiScript
import java.io.File
import kotlin.script.experimental.api.*
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val file = args.firstOrNull()
        ?.let(::File)
        ?: run {
            println("Usage: render-ui <file>")
            exitProcess(1)
        }
    InkUiScript.evalFile(file)
        .onFailure { result ->
            result.reports
                .filter { it.severity > ScriptDiagnostic.Severity.INFO }
                .forEach {
                    println(it.message)
                    it.exception?.printStackTrace()
                }
            exitProcess(1)
        }
        .valueOrThrow()
        .returnValue
        .scriptInstance
        .let { it as InkUiScript }
        .run {
            print(getHtml())
        }
}
