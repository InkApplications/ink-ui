package ink.ui.render.compose.renderer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.FormattedText
import ink.ui.structures.elements.TextListElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

object TextListRenderer: ElementRenderer
{
    @Composable
    override fun render(
        element: UiElement,
        theme: ComposeRenderTheme,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is TextListElement) {
            return RenderResult.Skipped
        }

        Column(
            modifier = Modifier.padding(start = theme.spacing.item)
        ) {
            element.items.forEachIndexed { index, item ->
                val prefix = if (element.ordered) "${index + 1}. " else " - "
                val prefixSpan: FormattedText.Span = FormattedText.Span.Text(prefix)
                val modified = item.copy(
                    spans = listOf(prefixSpan) + item.spans,
                    paragraph = false,
                )

                parent.render(modified, theme, parent)
            }
        }

        return RenderResult.Rendered
    }
}


