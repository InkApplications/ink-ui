package ink.ui.render.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ink.ui.render.compose.renderer.*
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.GroupingStyle
import ink.ui.structures.Positioning
import ink.ui.structures.layouts.*
import ink.ui.structures.render.MissingRendererBehavior
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.flow.MutableStateFlow

class ComposePresenter(
    renderers: List<ElementRenderer> = emptyList(),
    missingRendererBehavior: MissingRendererBehavior = MissingRendererBehavior.Placeholder(),
): Presenter {
    private val builtInRenderers = listOf(
        ActivityRenderer,
        ButtonRenderer,
        TextRenderer,
        StatusIndicatorRenderer,
        EmptyRenderer,
        ListRenderer,
        CheckboxRenderer,
        MenuRowRenderer,
        SpinnerRenderer,
        IconRenderer,
        WeatherRenderer,
        DividerRenderer,
        TextListRenderer,
        FormattedTextRenderer(),
    )
    internal val uiRenderer = CompositeElementRenderer(renderers + builtInRenderers, missingRendererBehavior)
    private val currentLayout = MutableStateFlow<UiLayout>(EmptyLayout)

    @Composable
    fun bind(theme: ComposeRenderTheme)
    {
        val state = currentLayout.collectAsState()

        render(state.value, theme)
    }

    override fun presentLayout(layout: UiLayout)
    {
        currentLayout.value = layout
    }

    @Composable
    internal fun render(uiLayout: UiLayout, theme: ComposeRenderTheme)
    {
        when (uiLayout) {
            is CenteredElementLayout -> Box(
                modifier = Modifier.Companion.background(theme.colors.background)
                    .fillMaxSize()
                    .padding(theme.spacing.gutters)
                    .windowInsetsPadding(WindowInsets.Companion.safeContent),
                contentAlignment = Alignment.Companion.Center,
            ) {
                uiRenderer.render(uiLayout.body, theme, uiRenderer)
            }
            is FixedGridLayout -> LazyVerticalGrid(
                contentPadding = WindowInsets(
                    top = theme.spacing.gutters - theme.spacing.item,
                    bottom = theme.spacing.gutters - theme.spacing.item,
                    left = theme.spacing.gutters - theme.spacing.item,
                    right = theme.spacing.gutters - theme.spacing.item,
                ).add(WindowInsets.Companion.safeContent).asPaddingValues(),
                columns = GridCells.Fixed(uiLayout.columns),
                modifier = Modifier.Companion.background(theme.colors.background)
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
                                && item.horizontalPositioning == Positioning.Center -> Alignment.Companion.Center

                            item.verticalPositioning == Positioning.Center -> Alignment.Companion.CenterStart
                            item.horizontalPositioning == Positioning.Center -> Alignment.Companion.TopCenter
                            else -> Alignment.Companion.TopStart
                        },
                        modifier = Modifier.Companion
                            .fillMaxSize()
                            .padding(theme.spacing.item)
                    ) {
                        uiRenderer.render(item.body, theme, uiRenderer)
                    }
                }
            }
            is ScrollingListLayout -> {
                val itemSpacing = when (uiLayout.groupingStyle) {
                    GroupingStyle.Unified, GroupingStyle.Inline -> 0.dp
                    GroupingStyle.Items -> theme.spacing.item
                    GroupingStyle.Sections -> theme.spacing.sectionSpacing
                }
                LazyColumn(
                    contentPadding = WindowInsets(
                        top = theme.spacing.gutters - itemSpacing,
                        bottom = theme.spacing.gutters - itemSpacing,
                        left = theme.spacing.gutters - itemSpacing,
                        right = theme.spacing.gutters - itemSpacing,
                    ).add(WindowInsets.Companion.safeDrawing).asPaddingValues(),
                    modifier = Modifier.Companion
                        .background(theme.colors.background)
                        .fillMaxSize()
                ) {
                    items(uiLayout.items) { item ->
                        Box(
                            modifier = Modifier.Companion.padding(itemSpacing)
                                .fillMaxWidth(),
                        ) {
                            uiRenderer.render(item, theme, uiRenderer)
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
fun ComposePresenter.bindAndPresent(
    theme: ComposeRenderTheme,
    layout: UiLayout,
) {
    bind(theme)
    presentLayout(layout)
}
