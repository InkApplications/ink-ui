package example

import ink.ui.structures.Sentiment
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.ProgressElement
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.ThrobberElement

val ProgressIndicators = listOf(
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
