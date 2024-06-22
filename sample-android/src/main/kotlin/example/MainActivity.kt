package example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ink.ui.render.compose.ComposeRenderer
import ink.ui.render.compose.theme.defaultTheme

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRenderer(defaultTheme()).render(
                SampleScreen
            )
        }
    }
}
