package ink.ui.render.compose.renderer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import ink.ui.structures.elements.IconElement
import org.jetbrains.compose.resources.painterResource

internal val IconRenderer = renderer<IconElement> { theme, element ->
    Image(
        painterResource(element.symbol.resource),
        colorFilter = ColorFilter.tint(theme.colors.forSentiment(element.sentiment)),
        contentDescription = null,
        modifier = Modifier.size(theme.sizing.hintIcons),
    )
}
