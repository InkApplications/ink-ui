package ink.ui.render.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ink.ui.render.compose.renderer.*
import ink.ui.render.compose.renderer.ActivityRenderer
import ink.ui.render.compose.renderer.CompositeElementRenderer
import ink.ui.render.compose.renderer.EmptyRenderer
import ink.ui.render.compose.renderer.TextRenderer
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.Positioning
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.*

/**
 * Renders UI Layouts and elements using Compose.
 */
class ComposeRenderer(
    private val theme: ComposeRenderTheme,
    renderers: List<ElementRenderer> = emptyList(),
) {
    private val builtInRenderers = listOf(
        ActivityRenderer,
        ButtonRenderer,
        TextRenderer,
        StatusIndicatorRenderer,
        EmptyRenderer,
        ListRenderer,
    )
    private val uiRenderer = CompositeElementRenderer(renderers + builtInRenderers)

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
        when (uiRenderer.render(element, theme, uiRenderer)) {
            RenderResult.NotRendered -> throw IllegalArgumentException("No renderer registered for ${element::class.simpleName}")
            RenderResult.Rendered -> {}
        }
    }
}
