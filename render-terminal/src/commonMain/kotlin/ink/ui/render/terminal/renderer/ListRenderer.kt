package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

object ListRenderer: ElementRenderer
{
    override suspend fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is ElementList) return RenderResult.Skipped

        element.items.forEach {
            parent.render(it, parent)
        }

        return RenderResult.Rendered
    }
}
