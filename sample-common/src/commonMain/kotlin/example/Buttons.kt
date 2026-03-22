package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.*

val Buttons = ElementList.build(groupingStyle = Items) {
    button(
        text = "Button",
        onClick = Action.Handler { println("Clicked") },
        sentiment = Sentiment.Primary,
    )
    button(
        text = "Link Button",
        onClick = Action.Link("#test"),
    )
    button(
        text = "Styled Link",
        onClick = Action.Link("#test-styled"),
        sentiment = Sentiment.Positive,
        leadingSymbol = Symbol.Bed,
    )
    button(
        text = "Button",
        onClick = Action.Handler { println("Clicked") },
        leadingSymbol = Symbol.LockOpen,
        sentiment = Sentiment.Primary,
        latchOnPress = true,
    )
    button(
        text = "Button w/Context",
        onContextClick = Action.Handler { println("Context Clicked") },
        leadingSymbol = Symbol("test"),
        sentiment = Sentiment.Caution,
        latchOnPress = true,
    )
}
