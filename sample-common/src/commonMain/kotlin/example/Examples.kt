package example

import ink.ui.structures.GroupingStyle
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.ScrollingListLayout

data class Examples(
    val elements: List<Pair<ElementCategory, UiElement>>
) {
    val layout get() = ScrollingListLayout(
        items = elements
            .groupBy { it.first }
            .map { it.key to it.value.map { it.second } }
            .map { (category, elements) ->
                items(TextElement(category.name, TextStyle.H1), *elements.toTypedArray())
            },
        groupingStyle = GroupingStyle.Sections,
    )

    fun withDefaults(): Examples
    {
        return copy(
            elements = elements + listOf(
                Pair(ElementCategory.Typography, Typography),
                Pair(ElementCategory.Buttons, Buttons),
                Pair(ElementCategory.ProgressIndicators, ProgressIndicators),
                Pair(ElementCategory.StatusIndicators, StatusIndicators),
                Pair(ElementCategory.Symbols, Symbols),
            )
        )
    }

    companion object
    {
        val Defaults = Examples(emptyList()).withDefaults()
    }
}
