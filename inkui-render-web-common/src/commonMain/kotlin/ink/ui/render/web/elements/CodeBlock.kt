package ink.ui.render.web.elements

import ink.ui.structures.elements.UiElement
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
