package ink.ui.render.compose.renderer

import ink.ui.render.compose.elements.Button
import ink.ui.structures.elements.ButtonElement

internal val ButtonRenderer = renderer<ButtonElement> { theme, element ->
    Button(
        text = element.text,
        sentiment = element.sentiment,
        latching = element.latchOnPress,
        leadingSymbol = element.leadingSymbol,
        trailingSymbol = element.trailingSymbol,
        theme = theme,
        onClick = element.onClick,
        onLongClick = element.onContextClick,
    )
}
