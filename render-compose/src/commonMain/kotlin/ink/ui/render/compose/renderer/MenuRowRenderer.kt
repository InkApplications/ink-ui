package ink.ui.render.compose.renderer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.MenuRowElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.render.RenderResult

internal object MenuRowRenderer: ElementRenderer {
    @Composable
    override fun render(element: UiElement, theme: ComposeRenderTheme, parent: ElementRenderer): RenderResult {
        if (element !is MenuRowElement) return RenderResult.Skipped

        val onClick = element.onClick
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .let {
                    if (onClick == null) it
                    else it.clickable(
                        onClick = onClick,
                        interactionSource = MutableInteractionSource(),
                        indication = null,
                    )
                }
        ) {
            BasicText(
                text = element.text,
                style = theme.typography.body.copy(
                    color = theme.colors.foreground,
                ),
                modifier = Modifier.padding(
                    end = theme.spacing.item,
                ),
            )

            val rightElement = element.rightElement
            if (rightElement != null) {
                parent.render(rightElement, theme, parent)
            }
        }

        return RenderResult.Rendered
    }
}
