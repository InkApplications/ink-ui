package ink.ui.render.statichtml

/**
 * Render specification for a page.
 */
data class PageProperties(
    var title: String,
    var inkFooter: Boolean = false,
    var contentBreak: Boolean = true,
    val meta: Meta = Meta(),
) {
    data class Meta(
        var deviceViewport: Boolean = true,
        var robots: String? = null,
        var author: String? = null,
        var keywords: String? = null,
    )
}
