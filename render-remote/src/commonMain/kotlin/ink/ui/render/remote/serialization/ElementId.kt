package ink.ui.render.remote.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Unique identifier used to track specific elements across serialization.
 */
@OptIn(ExperimentalUuidApi::class)
@Serializable(with = ElementIdSerializer::class)
@JvmInline
value class ElementId(val uuid: Uuid)
{
    constructor(): this(Uuid.random())
}

@OptIn(ExperimentalUuidApi::class)
internal class ElementIdSerializer: KSerializer<ElementId>
{
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    override fun serialize(encoder: Encoder, value: ElementId)
    {
        encoder.encodeString(value.uuid.toHexString())
    }

    override fun deserialize(decoder: Decoder): ElementId
    {
        val uuid = decoder.decodeString().let(Uuid::parse)
        return ElementId(uuid)
    }

}
