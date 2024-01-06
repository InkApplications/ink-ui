package ink.ui.render.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.elements.Button
import ink.ui.render.compose.elements.ProgressBar
import ink.ui.render.compose.elements.StatusIndicator
import ink.ui.render.compose.elements.Throbber
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Positioning
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.*
import ink.ui.structures.layouts.*

/**
 * Renders UI Layouts and elements using Compose.
 */
class ComposeRenderer(
    private val theme: ComposeRenderTheme,
) {
    @Composable
    fun render(uiLayout: UiLayout) {
        when (uiLayout) {
            is CenteredElementLayout -> Box(
                modifier = Modifier.background(theme.colors.background)
                    .fillMaxSize()
                    .padding(theme.spacing.gutters),
                contentAlignment = Alignment.Center,
            ) {
                renderElement(uiLayout.body)
            }
            is FixedGridLayout -> LazyVerticalGrid(
                contentPadding = PaddingValues(all = theme.spacing.gutters - theme.spacing.item),
                columns = GridCells.Fixed(uiLayout.columns),
                modifier = Modifier.background(theme.colors.background)
                    .fillMaxSize()
            ) {
                items(
                    items = uiLayout.items,
                    span = { item ->
                        GridItemSpan(item.span)
                    }
                ) { item ->
                    Box(
                        contentAlignment = when {
                            item.verticalPositioning == Positioning.Center
                                && item.horizontalPositioning == Positioning.Center -> Alignment.Center
                            item.verticalPositioning == Positioning.Center -> Alignment.CenterStart
                            item.horizontalPositioning == Positioning.Center -> Alignment.TopCenter
                            else -> Alignment.TopStart
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(theme.spacing.item)
                    ) {
                        renderElement(item.body)
                    }
                }
            }
            is PageLayout -> Box(
                modifier = Modifier.background(theme.colors.background)
                    .fillMaxSize()
                    .padding(theme.spacing.gutters),
            ){
                renderElement(uiLayout.body)
            }
            is ScrollingListLayout -> LazyColumn(
                contentPadding = PaddingValues(all = theme.spacing.gutters - theme.spacing.item),
                modifier = Modifier
                    .background(theme.colors.background)
                    .fillMaxSize()
            ) {
                items(uiLayout.items) { item ->
                    Box(
                        modifier = Modifier.padding(theme.spacing.item)
                            .fillMaxWidth(),
                    ) {
                        renderElement(item)
                    }
                }
            }
        }
    }

    @Composable
    fun renderElement(element: UiElement) {
        when (element) {
            is ButtonElement -> Button(
                text = element.text,
                sentiment = element.sentiment,
                latching = element.latchOnPress,
                theme = theme,
                onClick = element.onClick,
            )
            is ElementList -> Column(
                horizontalAlignment = when (element.positioning) {
                    Positioning.Start -> Alignment.Start
                    Positioning.Center -> Alignment.CenterHorizontally
                },
            ) {
                element.items.forEachIndexed { index, item ->
                    renderElement(item)
                    if (index != 0 && index != element.items.size - 1) {
                        Spacer(modifier = Modifier.height(theme.spacing.item))
                    }
                }
            }
            is ProgressElement -> ProgressBar(
                element = element,
                theme = theme
            )
            is StatusIndicatorElement -> StatusIndicator(
                text = element.text,
                sentiment = element.sentiment,
                theme = theme,
            )
            is TextElement -> BasicText(
                text = element.text,
                style = when (element.style) {
                    TextStyle.H1 -> theme.typography.h1
                    TextStyle.H2 -> theme.typography.h2
                    TextStyle.H3 -> theme.typography.h3
                    TextStyle.Body -> theme.typography.body
                    TextStyle.Caption -> theme.typography.caption
                }.copy(color = theme.colors.foreground),
            )
            is ThrobberElement -> Throbber(
                caption = element.caption,
                sentiment = element.sentiment,
                theme = theme
            )
            is EmptyElement -> Spacer(Modifier)
        }
    }
}
