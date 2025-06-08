package example

import kotlin.jvm.JvmInline

@JvmInline
value class ElementCategory(val name: String)
{
    companion object {
        val Navigation = ElementCategory("Navigation")
        val Typography = ElementCategory("Typography")
        val StatusIndicators = ElementCategory("Status Indicators")
        val ProgressIndicators = ElementCategory("Progress Indicators")
        val Symbols = ElementCategory("Symbols")
        val Buttons = ElementCategory("Buttons")
        val Forms = ElementCategory("Forms")
    }
}
