package example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.inkapplications.ui.example.R
import ink.ui.render.compose.ComposeRenderer
import ink.ui.render.compose.theme.ColorVariant
import ink.ui.render.compose.theme.ComposeRenderTheme
import ink.ui.render.compose.theme.TypographyVariant

class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val font = FontFamily(
                Font(R.font.roboto_mono_light, FontWeight.Normal),
                Font(R.font.roboto_mono_medium, FontWeight.Bold),
            )
            ComposeRenderer(
                ComposeRenderTheme(
                    typography = TypographyVariant().withFontFamily(font),
                    colors = if (isSystemInDarkTheme()) {
                        ColorVariant.Defaults.dark
                    } else {
                        ColorVariant.Defaults.light
                    }
                )
            ).render(
                SampleScreen
            )
        }
    }
}
