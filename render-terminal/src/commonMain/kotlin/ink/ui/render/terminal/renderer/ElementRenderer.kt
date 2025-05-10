package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

interface ElementRenderer
{
    suspend fun render(element: UiElement, parent: ElementRenderer): RenderResult
}
