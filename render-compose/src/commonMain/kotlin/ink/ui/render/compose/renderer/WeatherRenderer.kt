package ink.ui.render.compose.renderer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.elements.WeatherElement
import org.jetbrains.compose.resources.painterResource

val WeatherRenderer = renderer<WeatherElement> { theme, element ->
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            val title = element.title
            if (title != null) {
                BasicText(
                    text = title,
                    style = theme.typography.caption.copy(color = theme.colors.foreground),
                )
            }

            when (element.condition) {
                WeatherElement.Condition.Clear -> Image(
                    painterResource(Symbol.Sunshine.resource),
                    colorFilter = ColorFilter.tint(theme.colors.forSentiment(Sentiment.Nominal)),
                    contentDescription = element.condition.description,
                    modifier = Modifier.size(theme.sizing.widgetIcons).padding(theme.spacing.item)
                )

                WeatherElement.Condition.Cloudy -> Image(
                    painterResource(Symbol.Cloud.resource),
                    colorFilter = ColorFilter.tint(theme.colors.forSentiment(Sentiment.Nominal)),
                    contentDescription = element.condition.description,
                    modifier = Modifier.size(theme.sizing.widgetIcons).padding(theme.spacing.item)
                )

                WeatherElement.Condition.Rainy -> Image(
                    painterResource(Symbol.Rain.resource),
                    colorFilter = ColorFilter.tint(theme.colors.forSentiment(Sentiment.Nominal)),
                    contentDescription = element.condition.description,
                    modifier = Modifier.size(theme.sizing.widgetIcons).padding(theme.spacing.item)
                )
            }

            BasicText(element.temperature, style = theme.typography.body.copy(color = theme.colors.foreground))

            val secondaryTemperature = element.secondaryTemperature
            if (secondaryTemperature != null) {
                BasicText(secondaryTemperature, style = theme.typography.caption.copy(color = theme.colors.foreground))
            }
        }
    }
}

private val WeatherElement.Condition.description get() = when (this) {
    WeatherElement.Condition.Clear -> "Clear"
    WeatherElement.Condition.Cloudy -> "Cloudy"
    WeatherElement.Condition.Rainy -> "Rainy"
}
