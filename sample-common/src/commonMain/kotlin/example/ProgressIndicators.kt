package example

import ink.ui.structures.Sentiment
import ink.ui.structures.elements.ProgressElement
import ink.ui.structures.elements.ThrobberElement
import ink.ui.structures.elements.items

val ProgressIndicators = items(
    ProgressElement.Determinate(
        progress = 0.5f,
        caption = "Progress",
        sentiment = Sentiment.Positive
    ),
    ProgressElement.Indeterminate(
        caption = "Indeterminate Progress",
    ),
    ProgressElement.Indeterminate(
        caption = "Indeterminate Caution",
        sentiment = Sentiment.Caution
    ),
    ThrobberElement(
        caption = "Throbber",
    ),
    ThrobberElement(
        caption = "Error Throbber",
        sentiment = Sentiment.Negative,
    ),
)
