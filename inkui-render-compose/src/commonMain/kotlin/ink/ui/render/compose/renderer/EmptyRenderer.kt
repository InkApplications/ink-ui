package ink.ui.render.compose.renderer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.Modifier
import ink.ui.structures.elements.EmptyElement

internal val EmptyRenderer = renderer<EmptyElement> { theme, element ->
    Spacer(Modifier)
}
