package ink.ui.structures.render

import ink.ui.structures.layouts.UiLayout

internal class CompositePresenter(
    private val presenters: List<Presenter>,
) : Presenter {
    override fun presentLayout(layout: UiLayout)
    {
        presenters.forEach { it.presentLayout(layout) }
    }
}

fun Presenter.then(other: Presenter): Presenter
{
    return CompositePresenter(listOf(this, other))
}

fun Presenter.onUpdate(update: (UiLayout) -> Unit) = object: Presenter {
    override fun presentLayout(layout: UiLayout)
    {
        update(layout)
    }
}.then(this)
