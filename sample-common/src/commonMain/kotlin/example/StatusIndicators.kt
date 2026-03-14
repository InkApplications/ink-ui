package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.statusIndicator

val StatusIndicators = ElementList.build(groupingStyle = Items) {
    statusIndicator(
        text = "Nominal Status",
        sentiment = Sentiment.Nominal,
    )
    statusIndicator(
        text = "Primary Status",
        sentiment = Sentiment.Primary,
    )
    statusIndicator(
        text = "Positive Status",
        sentiment = Sentiment.Positive,
    )
    statusIndicator(
        text = "Negative Status",
        sentiment = Sentiment.Negative,
    )
    statusIndicator(
        text = "Caution Status",
        sentiment = Sentiment.Caution,
    )
    statusIndicator(
        text = "Idle Status",
        sentiment = Sentiment.Idle,
    )
}
