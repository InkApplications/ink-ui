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
    ThrobberElement(
        caption = "Throbber",
    ),
)
