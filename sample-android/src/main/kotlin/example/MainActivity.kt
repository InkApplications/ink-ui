package example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ink.ui.render.compose.ComposePresenter
import ink.ui.render.compose.bindAndPresent
import ink.ui.render.compose.theme.defaultTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val presenter = ComposePresenter(defaultTheme())

            presenter.bindAndPresent(SampleScreen)
        }
    }
}
