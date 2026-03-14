package ink.ui.structures.elements

import ink.ui.structures.ElementContainer
import ink.ui.structures.GroupingStyle
import ink.ui.structures.MutableListElementContainer
import ink.ui.structures.Positioning
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * UI element that composites multiple elements together.
 */
@Serializable
@SerialName("element-list")
data class ElementList(
    /**
     * Elements to be displayed in order.
     */
    val items: List<@Contextual UiElement>,

    /**
     * Positioning of each element within its own bounding box in the list.
     */
    val positioning: Positioning = Positioning.Start,

    /**
     * Signifiers for indicating how the elements are related to each other.
     */
    val groupingStyle: GroupingStyle = GroupingStyle.Items,

    /**
     * Direction of flow for the elements.
     */
    val orientation: Orientation = Orientation.Vertical,
): UiElement.Static {
    constructor(
        vararg items: UiElement,
        positioning: Positioning = Positioning.Start,
        groupingStyle: GroupingStyle = GroupingStyle.Items,
        orientation: Orientation = Orientation.Vertical,
    ): this(
        items = items.toList(),
        positioning = positioning,
        groupingStyle = groupingStyle,
        orientation = orientation,
    )

    companion object
    {
        fun build(
            positioning: Positioning = Positioning.Start,
            groupingStyle: GroupingStyle = GroupingStyle.Items,
            orientation: Orientation = Orientation.Vertical,
            builder: ElementContainer.() -> Unit,
        ): ElementList {
            return ElementList(
                items = MutableListElementContainer().apply(builder).build(),
                positioning = positioning,
                groupingStyle = groupingStyle,
                orientation = orientation,
            )
        }
    }
}

fun ElementContainer.inline(builder: ElementContainer.() -> Unit)
{
    ElementList.build(
        builder = builder,
        groupingStyle = GroupingStyle.Inline,
    ).also(::appendElement)
}

fun ElementContainer.unified(builder: ElementContainer.() -> Unit)
{
    ElementList.build(
        builder = builder,
        groupingStyle = GroupingStyle.Unified,
    ).also(::appendElement)
}

fun ElementContainer.sections(builder: ElementContainer.() -> Unit)
{
    ElementList.build(
        builder = builder,
        groupingStyle = GroupingStyle.Sections,
    ).also(::appendElement)
}

fun ElementContainer.items(builder: ElementContainer.() -> Unit)
{
    ElementList.build(
        builder = builder,
        groupingStyle = GroupingStyle.Sections,
    ).also(::appendElement)
}

