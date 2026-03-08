package ink.ui.render.statichtml.renderer

import ink.ui.render.web.elements.CodeBlock
import kotlinx.html.*

val CodeBlockRenderer = renderer<CodeBlock> { element ->
    pre(element.language?.key) {
        code {
            +element.code
        }
    }
}
