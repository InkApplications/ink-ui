package ink.ui.structures

import ink.ui.structures.elements.UiElement

interface ElementContainer
{
    fun append(element: UiElement)
}

fun ElementContainer.append(elements: List<UiElement>)
{
    elements.forEach(::append)
}

class MutableListElementContainer: ElementContainer
{
    private val elements = mutableListOf<UiElement>()

    override fun append(element: UiElement)
    {
        elements += element
    }

    fun build(): List<UiElement> = elements.toList()
}
