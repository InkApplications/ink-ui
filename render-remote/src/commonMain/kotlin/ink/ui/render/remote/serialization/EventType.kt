package ink.ui.render.remote.serialization

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class EventType(val id: String)
{
    companion object
    {
        val OnClick = EventType("onclick")
    }
}
