package ink.ui.render.compose.renderer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.CheckBoxElement
import ink.ui.structures.elements.UiElement

internal object CheckboxRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        if (element !is CheckBoxElement) return RenderResult.NotRendered

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

        return RenderResult.Rendered
    }
}
