package example

import ink.ui.structures.GroupingStyle
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate

object ExampleLayout
{
    private val elements = MutableStateFlow<List<Pair<ElementCategory, List<UiElement>>>>(emptyList())
    val categories get() = elements.value
        .groupBy { it.first }
        .map { (category, elements) ->
            category to elements.flatMap { it.second }
        }

    fun register(
        category: ElementCategory,
        elements: List<UiElement>,
    ) {
        ExampleLayout.elements.getAndUpdate { current ->
            current + Pair(category, elements)
        }
    }

    fun present(presenter: Presenter) {
        presenter.presentLayout(
            ScrollingListLayout(
                items = elements.value.flatMap { (category, elements) ->
                    listOf(
                        TextElement(category.name, TextStyle.H1),
                    ) + elements
                },
                groupingStyle = GroupingStyle.Sections,
            )
        )
    }
}

fun withDefaultElements() {
    ExampleLayout.register(ElementCategory.Typography, Typography)
    ExampleLayout.register(ElementCategory.Buttons, Buttons)
    ExampleLayout.register(ElementCategory.ProgressIndicators, ProgressIndicators)
    ExampleLayout.register(ElementCategory.StatusIndicators, StatusIndicators)
    ExampleLayout.register(ElementCategory.Symbols, Symbols)
}
