package ink.ui.structures.elements

import ink.ui.structures.Sentiment

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
