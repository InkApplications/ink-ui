package ink.ui.structures.elements

sealed interface Action
{
    data class Handler(
        val onInvoke: () -> Unit,
    ): Action

    data class Link(
        val url: String,
    ): Action
}
