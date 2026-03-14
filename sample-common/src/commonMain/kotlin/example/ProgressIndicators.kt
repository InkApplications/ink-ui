package example

import ink.ui.structures.GroupingStyle.Items
import ink.ui.structures.Sentiment
import ink.ui.structures.elements.*

val ProgressIndicators = ElementList.build(groupingStyle = Items) {
    determinateProgress(
        progress = 0.5f,
        caption = "Progress",
        sentiment = Sentiment.Positive
    )
    indeterminateProgress(
        caption = "Indeterminate Progress",
    )
    indeterminateProgress(
        caption = "Indeterminate Caution",
        sentiment = Sentiment.Caution
    )
    throbber(
        caption = "Throbber",
    )
    throbber(
        caption = "Error Throbber",
        sentiment = Sentiment.Negative,
    )
}
