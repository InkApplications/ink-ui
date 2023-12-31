package ink.ui.render.compose.theme

/**
 * Theming parameters when rendering compose views.
 */
data class ComposeRenderTheme(
    /**
     * Specifies the spacing between and within elements on the screen.
     */
    val spacing: SpacingVariant = SpacingVariant(),

    /**
     * Specifies the sizing of different elements on the screen.
     */
    val sizing: SizingVariant = SizingVariant(),

    /**
     * Specifies the colors used for elements on the screen.
     */
    val colors: ColorVariant = ColorVariant.Defaults.light,

    /**
     * Text styles used within the screens.
     */
    val typography: TypographyVariant = TypographyVariant(),
)
