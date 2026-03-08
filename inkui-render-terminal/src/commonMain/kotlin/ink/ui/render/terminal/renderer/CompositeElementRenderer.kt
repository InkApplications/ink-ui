package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyles
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.RenderResult
import ink.ui.structures.render.renderCatching

class CompositeElementRenderer(
    private val renderers: List<ElementRenderer> = emptyList(),
    private val missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): ElementRenderer {
    override suspend fun render(element: UiElement, parent: ElementRenderer): RenderResult {
        renderers.forEach { renderer ->
            val result = renderCatching { renderer.render(element, parent) }

            when (result) {
                RenderResult.Skipped -> {}
                is RenderResult.Failed -> throw result.exception
                RenderResult.Rendered -> return result
            }
        }

        when (missingRendererBehavior) {
            is MissingRendererBehavior.Placeholder -> {
                print(TextStyles.bold(TextColors.red("{{ ${element::class.simpleName} }}")))
            }
            is MissingRendererBehavior.Ignore -> {
                missingRendererBehavior.log(element)
            }
            MissingRendererBehavior.Panic -> throw MissingRendererBehavior.Panic.MissingRendererException(element)
        }

        return RenderResult.Skipped
    }
}
