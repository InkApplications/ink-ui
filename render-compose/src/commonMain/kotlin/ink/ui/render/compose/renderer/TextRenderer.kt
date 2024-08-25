package ink.ui.render.compose.renderer

import androidx.compose.foundation.text.BasicText
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.TextElement

internal val TextRenderer = renderer<TextElement> { theme, element ->
    BasicText(
        text = element.text,
        style = when (element.style) {
            TextStyle.H1 -> theme.typography.h1
            TextStyle.H2 -> theme.typography.h2
            TextStyle.H3 -> theme.typography.h3
            TextStyle.Body -> theme.typography.body
            TextStyle.Caption -> theme.typography.caption
        }.copy(color = theme.colors.foreground),
    )
}
