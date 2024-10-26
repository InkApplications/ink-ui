package ink.ui.structures

/**
 * Determines the placement of elements in a group.
 */
enum class GroupingStyle {
    /**
     * Treat the elements as a singular grouping.
     */
    Unified,

    /**
     * Treat the items as separate elements.
     */
    Items,

    /**
     * Treat the elements as unrelated sections.
     */
    Sections,

    /**
     * List each element one after another without any container.
     */
    Inline,
}
