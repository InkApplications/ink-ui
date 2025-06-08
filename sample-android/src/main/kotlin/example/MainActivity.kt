package example

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ink.ui.render.compose.ComposePresenter
import ink.ui.render.compose.renderer.FormattedTextRenderer
import ink.ui.render.compose.theme.defaultTheme
import androidx.core.net.toUri

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val presenter = ComposePresenter(
                theme = defaultTheme(),
                renderers = listOf(
                    FormattedTextRenderer(
                        onClickUrl = { url ->
                            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                            startActivity(intent)
                        }
                    )
                )
            )
            presenter.bind()

            presenter.presentLayout(Examples.Defaults.layout)
        }
    }
}
