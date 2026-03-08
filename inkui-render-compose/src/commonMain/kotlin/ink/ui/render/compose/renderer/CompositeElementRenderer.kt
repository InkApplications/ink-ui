package ink.ui.render.compose.renderer

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.MissingRendererBehavior.Panic.MissingRendererException
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
    private val missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderCatching { renderer.render(element, theme, this) }

            if (result != RenderResult.Skipped) {
                return result
            }
        }

        when (missingRendererBehavior) {
            is MissingRendererBehavior.Placeholder -> {
                missingRendererBehavior.log(element)
                Box(
                    modifier = Modifier.Companion.fillMaxSize()
                        .background(theme.colors.surface)
                        .border(theme.sizing.borders, theme.colors.negative, RoundedCornerShape(theme.sizing.corners))
                        .padding(theme.spacing.item),
                    contentAlignment = Alignment.Companion.Center,
                ) {
                    BasicText(
                        text = "{{ ${element::class.simpleName} }}",
                        style = theme.typography.caption,
                    )
                }
            }
            is MissingRendererBehavior.Ignore -> {
                missingRendererBehavior.log(element)
            }
            is MissingRendererBehavior.Panic -> throw MissingRendererException(element)
        }

        return RenderResult.Skipped
    }
}
