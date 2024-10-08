package ink.ui.render.web

import ink.ui.structures.Sentiment

fun Sentiment.toCssClass(): String {
    return when (this) {
        Sentiment.Nominal -> "nominal"
        Sentiment.Primary -> "primary"
        Sentiment.Positive -> "positive"
        Sentiment.Negative -> "negative"
        Sentiment.Caution -> "caution"
        Sentiment.Idle -> "idle"
    }
}
