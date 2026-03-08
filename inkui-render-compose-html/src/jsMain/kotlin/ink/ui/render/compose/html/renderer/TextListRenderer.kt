package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.TextListElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Ol
import org.jetbrains.compose.web.dom.Ul

object TextListRenderer: ElementRenderer {
    @Composable
    override fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is TextListElement) return RenderResult.Skipped

        if (element.ordered) {
            Ol {
                element.items.map { it.copy(paragraph = false) }.forEach { item ->
                    Li {
                        parent.render(item, parent)
                    }
                }
            }
        } else {
            Ul {
                element.items.map { it.copy(paragraph = false) }.forEach { item ->
                    Li {
                        parent.render(item, parent)
                    }
                }
            }
        }

        return RenderResult.Rendered
    }
}
