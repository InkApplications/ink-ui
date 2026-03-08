package ink.ui.render.remote.serialization

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

/**
 * Defines how a layout is encoded for transmission.
 */
@Serializable
@JvmInline
value class LayoutEncoding(val key: String)
{
    companion object
    {
        val Plain = LayoutEncoding("plain")
    }
}
