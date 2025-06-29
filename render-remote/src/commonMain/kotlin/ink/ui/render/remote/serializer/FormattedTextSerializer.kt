package ink.ui.render.remote.serializer

import ink.ui.structures.elements.FormattedText
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class FormattedTextSerializer: KSerializer<FormattedText>
{
    private val surrogateSerializer = FormattedTextSurrogate.serializer()
    override val descriptor = surrogateSerializer.descriptor
    override fun serialize(encoder: kotlinx.serialization.encoding.Encoder, value: FormattedText) {
        val surrogate = FormattedTextSurrogate(
            spans = value.spans.map { it.toSurrogate() },
            paragraph = value.paragraph,
        )
        surrogateSerializer.serialize(encoder, surrogate)
    }
    override fun deserialize(decoder: kotlinx.serialization.encoding.Decoder): FormattedText {
        val surrogate = surrogateSerializer.deserialize(decoder)
        return FormattedText(
            spans = surrogate.spans.map { it.toSpan() },
            paragraph = surrogate.paragraph,
        )
    }

    private fun FormattedText.Span.toSurrogate(): SpanSurrogate
    {
        return SpanSurrogate(
            type = when (this) {
                is FormattedText.Span.Text -> "text"
                is FormattedText.Span.Strong -> "strong"
                is FormattedText.Span.Emphasis -> "emphasis"
                is FormattedText.Span.Code -> "code"
                is FormattedText.Span.Link -> "link"
                is FormattedText.Span.Superscript -> "superscript"
                is FormattedText.Span.Subscript -> "subscript"
                FormattedText.Span.Break -> "break"
            },
            inner = (this as? FormattedText.Span.Composite)?.inner?.map { it.toSurrogate() },
            text = (this as? FormattedText.Span.Text)?.text,
            group = (this as? FormattedText.Span.Code)?.group,
            url = (this as? FormattedText.Span.Link)?.url,
        )
    }

    private fun SpanSurrogate.toSpan(): FormattedText.Span
    {
        return when (type) {
            "text" -> FormattedText.Span.Text(text ?: "")
            "strong" -> FormattedText.Span.Strong(inner?.map { it.toSpan() } ?: emptyList())
            "emphasis" -> FormattedText.Span.Emphasis(inner?.map { it.toSpan() } ?: emptyList())
            "code" -> FormattedText.Span.Code(inner?.map { it.toSpan() } ?: emptyList(), group ?: false)
            "link" -> FormattedText.Span.Link(inner?.map { it.toSpan() } ?: emptyList(), url ?: "")
            "superscript" -> FormattedText.Span.Superscript(inner?.map { it.toSpan() } ?: emptyList())
            "subscript" -> FormattedText.Span.Subscript(inner?.map { it.toSpan() } ?: emptyList())
            "break" -> FormattedText.Span.Break
            else -> throw IllegalArgumentException("Unknown span type: $type")
        }
    }

    @Serializable
    @SerialName("formatted-text")
    private data class FormattedTextSurrogate(
        val spans: List<SpanSurrogate>,
        val paragraph: Boolean = true,
    )

    @Serializable
    private data class SpanSurrogate(
        val type: String,
        val inner: List<SpanSurrogate>? = null,
        val text: String? = null,
        val group: Boolean? = null,
        val url: String? = null,
    )
}
