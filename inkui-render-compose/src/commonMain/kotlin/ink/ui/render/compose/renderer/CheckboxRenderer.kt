package ink.ui.render.compose.renderer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.structures.elements.CheckBoxElement

internal val CheckboxRenderer = renderer<CheckBoxElement> { theme, element ->
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = element.onClick)
            .padding(theme.spacing.clickSafety / 2)
    ) {
        BasicText(
            text = "[",
            style = theme.typography.uiGlyph.copy(
                color = theme.colors.foreground,
            ),
        )

        val centerChar = if (element.checked) "x" else " "
        val centerColor = theme.colors.foreground

        BasicText(
            text = centerChar,
            style = theme.typography.uiGlyph.copy(
                color = centerColor,
            ),
        )
        BasicText(
            text = "]",
            style = theme.typography.uiGlyph.copy(
                color = theme.colors.foreground,
            ),
        )
    }
}
