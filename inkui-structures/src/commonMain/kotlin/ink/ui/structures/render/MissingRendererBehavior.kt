package ink.ui.structures.render

import ink.ui.structures.elements.UiElement

sealed interface MissingRendererBehavior
{
    data class Ignore(val log: (UiElement) -> Unit = {}): MissingRendererBehavior
    data class Placeholder(val log: (UiElement) -> Unit = {}): MissingRendererBehavior
    data object Panic: MissingRendererBehavior
    {
        class MissingRendererException(
            element: UiElement,
        ): Exception("No renderer found for element: ${element::class.simpleName}")
    }
}
