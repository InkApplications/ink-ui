package ink.ui.structures.render

import ink.ui.structures.elements.UiElement

sealed interface RenderResult {
    data object Rendered: RenderResult
    data object Skipped: RenderResult
    data class Failed(val exception: Throwable): RenderResult
}

inline fun renderCatching(
    block: () -> RenderResult,
): RenderResult {
    return runCatching(block).getOrElse { RenderResult.Failed(it) }
}

inline fun <reified T: UiElement> UiElement.renderWithType(
    block: T.() -> Unit,
): RenderResult {
    return if (this !is T) RenderResult.Skipped
    else renderCatching { block(this); RenderResult.Rendered }
}
