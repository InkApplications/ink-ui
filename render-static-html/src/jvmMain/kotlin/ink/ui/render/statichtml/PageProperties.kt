package ink.ui.render.statichtml

/**
 * Render specification for a page.
 */
data class PageProperties(
    var title: String,
    var inkFooter: Boolean = false,
    var sectioned: Boolean = false,
    var contentBreak: Boolean = false,
)
