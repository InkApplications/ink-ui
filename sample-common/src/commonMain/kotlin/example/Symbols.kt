package example

import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.IconElement

val Symbols = listOf(
    IconElement(
        symbol = Symbol.Add,
        sentiment = Sentiment.Primary,
    ),
    IconElement(
        symbol = Symbol.Alarm,
        sentiment = Sentiment.Positive,
    ),
    IconElement(
        symbol = Symbol.AlarmOff,
        sentiment = Sentiment.Negative,
    ),
    IconElement(
        symbol = Symbol.ArrowBackward,
        sentiment = Sentiment.Caution,
    ),
    IconElement(
        symbol = Symbol.ArrowForward,
        sentiment = Sentiment.Idle,
    ),
    IconElement(Symbol.Bed),
    IconElement(Symbol.Caution),
    IconElement(Symbol.Close),
    IconElement(Symbol.Cloud),
    IconElement(Symbol.Done),
    IconElement(Symbol.Edit),
    IconElement(Symbol.Error),
    IconElement(Symbol.Group),
    IconElement(Symbol.House),
    IconElement(Symbol.Lock),
    IconElement(Symbol.LockOpen),
    IconElement(Symbol.Moon),
    IconElement(Symbol.Movie),
    IconElement(Symbol.Person),
    IconElement(Symbol.Rain),
    IconElement(Symbol.Remove),
    IconElement(Symbol.Sunshine),
    IconElement(Symbol.Temperature),
    IconElement(Symbol.Water),
)
