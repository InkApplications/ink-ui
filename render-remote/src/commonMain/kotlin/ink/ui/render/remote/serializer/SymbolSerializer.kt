package ink.ui.render.remote.serializer

import ink.ui.structures.Symbol
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object SymbolSerializer: KSerializer<Symbol>
{
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Symbol",
        kind = PrimitiveKind.STRING
    )

    override fun serialize(encoder: Encoder, value: Symbol)
    {
        encoder.encodeString(value.key)
    }

    override fun deserialize(decoder: Decoder): Symbol
    {
        return decoder.decodeString().let(::Symbol)
    }
}
