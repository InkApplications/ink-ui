package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.MissingRendererBehavior.Panic.MissingRendererException
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

internal class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
    private val missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): ElementRenderer {
    @Composable
    override fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderCatching { renderer.render(element, this) }

            if (result != RenderResult.Skipped) {
                return result
            }
        }

        when (missingRendererBehavior) {
            is MissingRendererBehavior.Panic -> throw MissingRendererException(element)
            is MissingRendererBehavior.Placeholder -> {
                missingRendererBehavior.log(element)
                Div(
                    attrs = {
                        classes("error-box")
                    },
                ) {
                    Text("{{ ${element::class.simpleName} }}")
                }
            }
            is MissingRendererBehavior.Ignore -> {
                missingRendererBehavior.log(element)
            }
        }

        return RenderResult.Skipped
    }
}
