package ink.ui.render.compose.renderer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import ink.ui.render.compose.theme.ColorVariant
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Symbol
import ink.ui.structures.elements.WeatherElement
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Deprecated("Will be removed from core UI library")
val WeatherRenderer = renderer<WeatherElement> { theme, element ->
    val description = when (element.condition) {
        WeatherElement.Condition.Clear -> "Clear"
        WeatherElement.Condition.Cloudy -> "Cloudy"
        WeatherElement.Condition.Rainy -> "Rainy"
        WeatherElement.Condition.Snowy -> "Snowy"
    }
    val icon = when (element.condition) {
        WeatherElement.Condition.Clear -> if (element.daytime) {
            Symbol.Sunshine.resource
        } else {
            Symbol.Moon.resource
        }
        WeatherElement.Condition.Cloudy -> Symbol.Cloud.resource
        WeatherElement.Condition.Rainy -> Symbol.Rain.resource
        WeatherElement.Condition.Snowy -> Symbol.Snow.resource
    }

    val sentiment = element.sentiment
    val tint = when {
        sentiment != null -> theme.colors.forSentiment(sentiment)
        element.condition == WeatherElement.Condition.Rainy -> ColorVariant.Defaults.Colors.BLUE
        element.condition == WeatherElement.Condition.Clear -> ColorVariant.Defaults.Colors.YELLOW
        element.condition == WeatherElement.Condition.Cloudy -> ColorVariant.Defaults.Colors.GRAY
        else -> theme.colors.foreground
    }.let(ColorFilter::tint)

    if (element.compact) {
        InlineRender(
            theme = theme,
            title = element.title,
            icon = icon,
            tint = tint,
            iconDescription = description,
            temperature = element.temperature,
            secondaryText = element.secondaryText,
        )
    } else {
        BoxRender(
            theme = theme,
            title = element.title,
            icon = icon,
            tint = tint,
            iconDescription = description,
            temperature = element.temperature,
            secondaryText = element.secondaryText,
        )
    }
}

@Composable
private fun InlineRender(
    theme: ComposeRenderTheme,
    title: String? = null,
    icon: DrawableResource,
    tint: ColorFilter,
    iconDescription: String,
    temperature: String,
    secondaryText: String?,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(theme.spacing.item)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (title != null && title.isNotBlank()) {
                BasicText(
                    text = title,
                    style = theme.typography.caption.copy(color = theme.colors.foreground),
                )
            }
            Image(
                painterResource(icon),
                colorFilter = tint,
                contentDescription = iconDescription,
                modifier = Modifier.size(theme.sizing.hintIcons)
            )
        }

        Spacer(modifier = Modifier.width(theme.spacing.item))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            BasicText(temperature, style = theme.typography.body.copy(color = theme.colors.foreground))

            if (secondaryText != null && secondaryText.isNotBlank()) {
                BasicText(secondaryText, style = theme.typography.caption.copy(color = theme.colors.foreground))
            }
        }
    }
}

@Composable
private fun BoxRender(
    theme: ComposeRenderTheme,
    title: String? = null,
    icon: DrawableResource,
    tint: ColorFilter,
    iconDescription: String,
    temperature: String,
    secondaryText: String?,
) {

    Box(
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (title != null) {
                BasicText(
                    text = title,
                    style = theme.typography.caption.copy(color = theme.colors.foreground),
                )
            }

            Image(
                painterResource(icon),
                colorFilter = tint,
                contentDescription = iconDescription,
                modifier = Modifier.size(theme.sizing.widgetIcons).padding(theme.spacing.item)
            )

            BasicText(temperature, style = theme.typography.body.copy(color = theme.colors.foreground))

            if (secondaryText != null) {
                BasicText(secondaryText, style = theme.typography.caption.copy(color = theme.colors.foreground))
            }
        }
    }
}
