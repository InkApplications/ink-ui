package ink.ui.render.web.elements

import ink.ui.structures.elements.UiElement

data class CodeBlock(
    val code: String,
    val language: Language? = null,
): UiElement.Static {
    enum class Language {
        Bash,
        Kotlin,
        Shell,
    }
}
