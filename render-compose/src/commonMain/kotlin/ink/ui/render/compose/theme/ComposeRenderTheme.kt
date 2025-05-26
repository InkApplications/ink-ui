package ink.ui.render.compose.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.inkapplications.ui.render_compose.generated.resources.*
import org.jetbrains.compose.resources.Font

/**
 * Theming parameters when rendering compose views.
 */
data class ComposeRenderTheme(
    /**
     * Specifies the spacing between and within elements on the screen.
     */
    val spacing: SpacingVariant,

    /**
     * Specifies the sizing of different elements on the screen.
     */
    val sizing: SizingVariant,

    /**
     * Specifies the colors used for elements on the screen.
     */
    val colors: ColorVariant,

    /**
     * Text styles used within the screens.
     */
    val typography: TypographyVariant,
)

@Composable
fun defaultTheme(): ComposeRenderTheme {
    return ComposeRenderTheme(
        spacing = SpacingVariant(),
        sizing = SizingVariant(),
        colors = if (isSystemInDarkTheme()) {
            ColorVariant.Defaults.dark
        } else {
            ColorVariant.Defaults.light
        },
        typography = TypographyVariant().withFontFamily(
            FontFamily(
                Font(Res.font.roboto_mono_light, FontWeight.Normal),
                Font(Res.font.roboto_mono_medium, FontWeight.Bold),
            )
        ),
    )
}
