package example

import ink.ui.structures.Sentiment
import ink.ui.structures.elements.StatusIndicatorElement
import ink.ui.structures.elements.items

val StatusIndicators = items(
    StatusIndicatorElement(
        text = "Nominal Status",
        sentiment = Sentiment.Nominal,
    ),
    StatusIndicatorElement(
        text = "Primary Status",
        sentiment = Sentiment.Primary,
    ),
    StatusIndicatorElement(
        text = "Positive Status",
        sentiment = Sentiment.Positive,
    ),
    StatusIndicatorElement(
        text = "Negative Status",
        sentiment = Sentiment.Negative,
    ),
    StatusIndicatorElement(
        text = "Caution Status",
        sentiment = Sentiment.Caution,
    ),
    StatusIndicatorElement(
        text = "Idle Status",
        sentiment = Sentiment.Idle,
    ),
)
