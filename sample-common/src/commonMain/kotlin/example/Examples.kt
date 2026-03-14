package example

import ink.ui.structures.GroupingStyle
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*

data class Examples(
    val elements: List<Pair<ElementCategory, UiElement>>
) {
    val groupedElements get() = elements
        .groupBy { it.first }
        .map { it.key to it.value.map { it.second } }
        .map { (category, elements) ->
            ElementList(
                items = listOf(
                    TextElement(category.name, TextStyle.H1),
                    *elements.toTypedArray()
                ),
                groupingStyle = GroupingStyle.Items
            )
        }
        .let { ElementList(it, groupingStyle = GroupingStyle.Sections) }

    fun withDefaults(): Examples
    {
        return copy(
            elements = elements + listOf(
                Pair(ElementCategory.Typography, Typography),
                Pair(ElementCategory.Buttons, Buttons),
                Pair(ElementCategory.ProgressIndicators, ProgressIndicators),
                Pair(ElementCategory.StatusIndicators, StatusIndicators),
                Pair(ElementCategory.Symbols, Symbols),
                Pair(ElementCategory.Forms, Forms),
            )
        )
    }

    companion object
    {
        val Defaults = Examples(emptyList()).withDefaults()
    }
}
