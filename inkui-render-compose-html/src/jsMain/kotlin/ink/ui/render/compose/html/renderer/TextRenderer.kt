package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.TextElement
import org.jetbrains.compose.web.dom.*

val TextRenderer = renderer<TextElement> { element ->
    when (element.style) {
        TextStyle.H1 -> H1 {
            Text(element.text)
        }
        TextStyle.H2 -> H2 {
            Text(element.text)
        }
        TextStyle.H3 -> H3 {
            Text(element.text)
        }
        TextStyle.Body -> P(
            attrs = {}
        ) {
            TextWithBreaks(element.text)
        }
        TextStyle.Caption -> P(
            attrs = {
                classes("caption")
            }
        ) {
            TextWithBreaks(element.text)
        }
    }
}

@Composable
private fun TextWithBreaks(text: String) {
    text.split("\n").forEachIndexed { index, line ->
        if (index > 0) {
            Br()
        }
        Text(line)
    }
}
