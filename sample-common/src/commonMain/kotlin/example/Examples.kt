package example

import ink.ui.structures.GroupingStyle
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.ScrollingListLayout

data class Examples(
    val elements: List<Pair<ElementCategory, List<UiElement>>>
)
{
    val layout get() = ScrollingListLayout(
        items = elements.flatMap { (category, elements) ->
            listOf(
                TextElement(category.name, TextStyle.H1),
            ) + elements
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
