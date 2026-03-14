package ink.ui.structures.render

import ink.ui.structures.elements.UiElement

internal class CompositePresenter(
    private val presenters: List<Presenter>,
) : Presenter {
    override fun presentElement(element: UiElement)
    {
        presenters.forEach { it.presentElement(element) }
    }
}

fun Presenter.then(other: Presenter): Presenter
{
    return CompositePresenter(listOf(this, other))
}

fun Presenter.onUpdate(update: (UiElement) -> Unit) = object: Presenter {
    override fun presentElement(element: UiElement)
    {
        update(element)
    }
}.then(this)
