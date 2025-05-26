package example

import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.ButtonElement

val Buttons = listOf(
    ButtonElement(
        text = "Button",
        onClick = { println("Clicked") },
        leadingSymbol = Symbol.LockOpen,
        sentiment = Sentiment.Primary,
        latchOnPress = true,
    ),
    ButtonElement(
        text = "Button w/Context",
        onClick = { println("Clicked") },
        onContextClick = { println("Context Clicked") },
        leadingSymbol = Symbol("test"),
        sentiment = Sentiment.Caution,
        latchOnPress = true,
    ),
)
