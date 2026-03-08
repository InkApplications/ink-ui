package ink.ui.structures

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Determines the placement of elements in a group.
 */
@Serializable
enum class GroupingStyle
{
    /**
     * Treat the elements as a singular grouping.
     */
    @SerialName("unified")
    Unified,

    /**
     * Treat the items as separate elements.
     */
    @SerialName("items")
    Items,

    /**
     * Treat the elements as unrelated sections.
     */
    @SerialName("sections")
    Sections,

    /**
     * List each element one after another without any container.
     */
    @SerialName("inline")
    Inline,
}
