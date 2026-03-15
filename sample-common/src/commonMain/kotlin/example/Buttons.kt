package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.*

val Buttons = ElementList.build(groupingStyle = Items) {
    button(
        text = "Button",
        onClick = { println("Clicked") },
        sentiment = Sentiment.Primary,
    )
    button(
        text = "Button",
        onClick = { println("Clicked") },
        leadingSymbol = Symbol.LockOpen,
        sentiment = Sentiment.Primary,
        latchOnPress = true,
    )
    button(
        text = "Button w/Context",
        onClick = { println("Clicked") },
        onContextClick = { println("Context Clicked") },
        leadingSymbol = Symbol("test"),
        sentiment = Sentiment.Caution,
        latchOnPress = true,
    )
}
