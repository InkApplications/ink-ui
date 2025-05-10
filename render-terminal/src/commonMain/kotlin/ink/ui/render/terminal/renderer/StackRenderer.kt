package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.StackElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult
import kotlinx.coroutines.CancellationException

object StackRenderer: ElementRenderer
{
    override suspend fun render(
        element: UiElement,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is StackElement) return RenderResult.Skipped

        try {
            element.queue.collect { element ->
                parent.render(element, parent)
            }
        } catch (e: CancellationException) {
            element.close()
            throw e
        }

        return RenderResult.Rendered
    }
}
