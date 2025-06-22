package ink.ui.render.remote.serialization

import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.CenteredElementLayout
import ink.ui.structures.layouts.EmptyLayout
import ink.ui.structures.layouts.FixedGridLayout
import ink.ui.structures.layouts.PageLayout
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

internal object LayoutSerializer: KSerializer<UiLayout>
{
    private val surrogate = LayoutSurrogate.serializer()
    override val descriptor: SerialDescriptor = surrogate.descriptor

    override fun serialize(encoder: Encoder, value: UiLayout)
    {
        val data = LayoutSurrogate(
            type = when (value) {
                is CenteredElementLayout -> "centered-element"
                EmptyLayout -> "empty"
                is FixedGridLayout -> "fixed-grid"
                is PageLayout -> "page"
                is ScrollingListLayout -> "scrolling-list"
            },
            element = when (value) {
                is CenteredElementLayout -> value.body
                is PageLayout -> value.body
                else -> null
            },
//            gridItems = (value as? FixedGridLayout)?.items,
            columns = (value as? FixedGridLayout)?.columns,
            items = (value as? ScrollingListLayout)?.items,
            groupingStyle = (value as? ScrollingListLayout)?.groupingStyle ?: GroupingStyle.Items,
        )
        surrogate.serialize(encoder, data)
    }

    override fun deserialize(decoder: Decoder): UiLayout
    {
        val data = surrogate.deserialize(decoder)

        return when (data.type) {
            "centered-element" -> return CenteredElementLayout(
                body = data.element ?: throw IllegalArgumentException("CenteredElementLayout requires a body element"),
            )
            "empty" -> EmptyLayout
            // TODO: Serializer for grid items
//            "fixed-grid" -> return FixedGridLayout(
//                columns = data.columns ?: throw IllegalArgumentException("FixedGridLayout requires a column count"),
//                items = data.gridItems
//                    ?: throw IllegalArgumentException("FixedGridLayout requires a list of grid items to display"),
//            )
            "page" -> return PageLayout(
                body = data.element ?: throw IllegalArgumentException("PageLayout requires a body element"),
            )
            "scrolling-list" -> return ScrollingListLayout(
                items = data.items
                    ?: throw IllegalArgumentException("ScrollingListLayout requires a list of items to display"),
                groupingStyle = data.groupingStyle ?: GroupingStyle.Items,
            )
            else -> throw IllegalArgumentException("Unknown layout type: ${data.type}")
        }
    }

    @Serializable
    private data class LayoutSurrogate(
        val type: String,
        @Contextual
        val element: UiElement? = null,
//        @Contextual
//        val gridItems: List<FixedGridLayout.GridItem>? = null,
        val items: List<@Contextual UiElement>? = null,
        val columns: Int? = null,
        val groupingStyle: GroupingStyle? = null,
    )
}
