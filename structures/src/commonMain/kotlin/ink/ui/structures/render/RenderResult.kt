package ink.ui.structures.render

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
