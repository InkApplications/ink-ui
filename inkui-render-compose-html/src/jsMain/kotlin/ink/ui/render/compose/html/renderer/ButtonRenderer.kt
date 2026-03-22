package ink.ui.render.compose.html.renderer

import androidx.compose.runtime.Composable
import ink.ui.render.web.composePath
import ink.ui.render.web.toCssClass
import ink.ui.structures.elements.Action
import ink.ui.structures.elements.ButtonElement
import kotlinx.browser.window
import org.jetbrains.compose.web.attributes.href
import org.jetbrains.compose.web.dom.*

val ButtonRenderer = renderer<ButtonElement> { element ->
    val onClick = element.onClick
    val onContextClick = element.onContextClick

    when (onClick) {
        null, is Action.Handler -> {
            Button(
                attrs = {
                    classes(element.sentiment.toCssClass())
                    onClick {
                        onClick?.onInvoke()
                    }
                    onContextMenu {
                        when (onContextClick) {
                            is Action.Handler -> {
                                it.preventDefault()
                                onContextClick.onInvoke()
                            }
                            is Action.Link -> {
                                it.preventDefault()
                                window.location.href = onContextClick.url
                            }
                            null -> {}
                        }
                    }
                }
            ) {
                interior(element)
            }
        }
        is Action.Link -> {
            A(
                attrs = {
                    classes("button", element.sentiment.toCssClass())
                    href(onClick.url)
                    onContextMenu {
                        when (onContextClick) {
                            is Action.Handler -> {
                                it.preventDefault()
                                onContextClick.onInvoke()
                            }
                            is Action.Link -> {
                                it.preventDefault()
                                window.location.href = onContextClick.url
                            }
                            null -> {}
                        }
                    }
                }
            ) {
                interior(element)
            }
        }
    }
}

@Composable
private fun interior(
    element: ButtonElement,
) {
    val leadingSymbol = element.leadingSymbol
    val trailingSymbol = element.trailingSymbol

    if (leadingSymbol != null) {
        Img(
            attrs = {
                classes("icon", "svg-fill", element.sentiment.toCssClass())
            },
            src = leadingSymbol.composePath,
        )
    }
    Text(element.text)
    if (trailingSymbol != null) {
        Img(
            attrs = {
                classes("icon", "svg-fill", element.sentiment.toCssClass())
            },
            src = trailingSymbol.composePath,
        )
    }
}
