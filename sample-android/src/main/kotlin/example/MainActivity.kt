package example

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ink.ui.render.compose.ComposeRenderer
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.structures.TextStyle
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.ScrollingListLayout
import ink.ui.structures.layouts.UiLayout

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeRenderer(
                ComposeRenderTheme()
            ).render(
                SampleScreen
            )
        }
    }
}
