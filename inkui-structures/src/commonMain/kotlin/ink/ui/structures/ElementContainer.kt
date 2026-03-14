package ink.ui.structures

import ink.ui.structures.elements.UiElement

interface ElementContainer
{
    fun appendElement(element: UiElement)
}

class MutableListElementContainer: ElementContainer
{
    private val elements = mutableListOf<UiElement>()

    override fun appendElement(element: UiElement)
    {
        elements += element
    }

    fun build(): List<UiElement> = elements.toList()
}
