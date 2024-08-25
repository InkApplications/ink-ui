package ink.ui.render.compose.renderer

import ink.ui.render.compose.elements.StatusIndicator
import ink.ui.structures.elements.StatusIndicatorElement

internal val StatusIndicatorRenderer = renderer<StatusIndicatorElement> { theme, element ->
    StatusIndicator(
        text = element.text,
        sentiment = element.sentiment,
        theme = theme,
    )
}
