package ink.ui.structures.elements

data class WeatherElement(
    val temperature: String,
    val condition: Condition,
    val secondaryTemperature: String? = null,
    val title: String? = null,
): UiElement.Static {
    enum class Condition {
        Clear,
        Cloudy,
        Rainy,
    }
}
