package ink.ui.render.compose.renderer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.structures.elements.SpinnerElement

internal val SpinnerRenderer = renderer<SpinnerElement> { theme, element ->
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .let { if (element.hasPreviousValue) it.clickable { element.onPreviousValue() } else it }
                .padding(theme.spacing.clickSafety / 2)
        ) {
            BasicText(
                text = "-",
                style = theme.typography.uiGlyph.copy(
                    color = if (element.hasPreviousValue) theme.colors.foreground else theme.colors.inactive,
                ),
            )
        }
        BasicText(
            text = element.value,
            style = theme.typography.body.copy(
                color = theme.colors.primary,
            ),
        )
        Box(
            modifier = Modifier
                .let { if (element.hasNextValue) it.clickable { element.onNextValue() } else it }
                .padding(theme.spacing.clickSafety / 2)
        ) {
            BasicText(
                text = "+",
                style = theme.typography.uiGlyph.copy(
                    color = if (element.hasNextValue) theme.colors.foreground else theme.colors.inactive,
                ),
            )
        }
    }
}
