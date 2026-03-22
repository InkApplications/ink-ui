package ink.ui.render.compose.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.compose.elements.Button
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.elements.Action
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.elements.UrlHandler
import ink.ui.structures.render.RenderResult

class ButtonRenderer(
    val urlHandler: UrlHandler? = null,
): ElementRenderer {
    @Composable
    override fun render(
        element: UiElement,
        theme: ComposeRenderTheme,
        parent: ElementRenderer,
    ): RenderResult {
        if (element !is ButtonElement) return RenderResult.Skipped
        val onClick = element.onClick
        val onContextClick = element.onContextClick

        if (urlHandler == null && (onClick is Action.Link || onContextClick is Action.Link)) {
            return RenderResult.Failed(
                IllegalArgumentException("ButtonElement has a link action, but no onUrlClick handler was provided to the ButtonRenderer.")
            )
        }

        Button(
            text = element.text,
            sentiment = element.sentiment,
            latching = element.latchOnPress,
            leadingSymbol = element.leadingSymbol,
            trailingSymbol = element.trailingSymbol,
            theme = theme,
            onClick = {
                when (onClick) {
                    is Action.Handler -> onClick.onInvoke()
                    is Action.Link -> urlHandler?.openUrl(onClick.url)
                    null -> {}
                }
            },
            onLongClick = {
                when (onContextClick) {
                    is Action.Handler -> onContextClick.onInvoke()
                    is Action.Link -> urlHandler?.openUrl(onContextClick.url)
                    null -> {}
                }
            },
        )

        return RenderResult.Rendered
    }
}
