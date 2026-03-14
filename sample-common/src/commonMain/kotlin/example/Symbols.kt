package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.Orientation
import ink.ui.structures.elements.icon

val Symbols = ElementList.build(
    groupingStyle = Items,
    orientation = Orientation.Horizontal,
) {
    icon(
        symbol = Symbol.Add,
        sentiment = Sentiment.Primary,
    )
    icon(
        symbol = Symbol.Alarm,
        sentiment = Sentiment.Positive,
    )
    icon(
        symbol = Symbol.AlarmOff,
        sentiment = Sentiment.Negative,
    )
    icon(
        symbol = Symbol.ArrowBackward,
        sentiment = Sentiment.Caution,
    )
    icon(
        symbol = Symbol.ArrowForward,
        sentiment = Sentiment.Idle,
    )
    icon(Symbol.Bed)
    icon(Symbol.Caution)
    icon(Symbol.Close)
    icon(Symbol.Cloud)
    icon(Symbol.Done)
    icon(Symbol.Edit)
    icon(Symbol.Error)
    icon(Symbol.Group)
    icon(Symbol.House)
    icon(Symbol.Lock)
    icon(Symbol.LockOpen)
    icon(Symbol.Moon)
    icon(Symbol.Movie)
    icon(Symbol.Person)
    icon(Symbol.Rain)
    icon(Symbol.Remove)
    icon(Symbol.Sunshine)
    icon(Symbol.Temperature)
    icon(Symbol.Water)
}
