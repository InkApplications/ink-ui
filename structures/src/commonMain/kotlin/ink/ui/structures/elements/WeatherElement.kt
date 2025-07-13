package ink.ui.structures.elements

import ink.ui.structures.Sentiment

@Deprecated("Will be removed from core UI library")
data class WeatherElement(
    val temperature: String,
    val condition: Condition,
    val secondaryText: String? = null,
    val title: String? = null,
    val daytime: Boolean = true,
    val sentiment: Sentiment? = null,
    val compact: Boolean = false,
): UiElement.Static {

    enum class Condition {
        Clear,
        Cloudy,
        Rainy,
        Snowy,
    }
}
