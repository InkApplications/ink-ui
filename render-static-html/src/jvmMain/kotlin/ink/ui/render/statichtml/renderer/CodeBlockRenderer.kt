package ink.ui.render.statichtml.renderer

import ink.ui.render.web.elements.CodeBlock
import ink.ui.render.web.elements.CodeBlock.Language.*
import kotlinx.html.*

val CodeBlockRenderer = renderer<CodeBlock> { element ->
    pre(when (element.language) {
        Bash -> "bash"
        Kotlin -> "kotlin"
        Shell -> "shell"
        null -> null
    }) {
        code {
            +element.code
        }
    }
}
