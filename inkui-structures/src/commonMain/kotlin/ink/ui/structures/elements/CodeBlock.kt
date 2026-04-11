package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import kotlin.jvm.JvmInline

data class CodeBlock(
    val code: String,
    val language: Language? = null,
): UiElement.Static {
    @JvmInline
    value class Language(val key: String) {
        companion object {
            val Bash = Language("bash")
            val Kotlin = Language("kotlin")
            val Shell = Language("shell")
        }
    }
}

fun ElementContainer.codeBlock(
    code: String,
    language: CodeBlock.Language? = null,
) {
    CodeBlock(
        code = code,
        language = language,
    ).also(::append)
}
