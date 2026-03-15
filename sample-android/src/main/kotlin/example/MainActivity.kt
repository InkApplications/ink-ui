package example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.Modifier
import ink.ui.render.compose.ComposePresenter
import ink.ui.render.compose.renderer.FormattedTextRenderer
import ink.ui.render.compose.theme.defaultTheme
import androidx.core.net.toUri
import ink.ui.structures.GroupingStyle
import ink.ui.structures.elements.ElementList

class MainActivity: ComponentActivity() {
    private val presenter = ComposePresenter(
        renderers = listOf(
            FormattedTextRenderer(
                onClickUrl = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    startActivity(intent)
                }
            )
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val theme = defaultTheme()
            val scrollState = rememberScrollState()

            Box(
                modifier = Modifier
                    .background(theme.colors.background)
                    .padding(
                        top = theme.spacing.gutters,
                        bottom = theme.spacing.gutters,
                        start = theme.spacing.gutters,
                        end = theme.spacing.gutters,
                    )
                    .padding(
                        WindowInsets.safeDrawing.asPaddingValues()
                    )
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            ) {
                presenter.bind(theme)
            }
        }

        presenter.presentElement(
            element = ElementList.build(groupingStyle = GroupingStyle.Inline) {
                appendElement(Breadcrumbs)
                appendElement(Examples.Defaults.groupedElements)
            },
        )
    }
}
