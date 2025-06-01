package ink.ui.render.terminal.renderer

import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.rendering.TextStyle
import ink.ui.structures.Sentiment
import ink.ui.structures.Symbol
import ink.ui.structures.Symbol.Companion.Add
import ink.ui.structures.Symbol.Companion.Alarm
import ink.ui.structures.Symbol.Companion.AlarmOff
import ink.ui.structures.Symbol.Companion.ArrowBackward
import ink.ui.structures.Symbol.Companion.ArrowForward
import ink.ui.structures.Symbol.Companion.Bed
import ink.ui.structures.Symbol.Companion.Caution
import ink.ui.structures.Symbol.Companion.Close
import ink.ui.structures.Symbol.Companion.Cloud
import ink.ui.structures.Symbol.Companion.Done
import ink.ui.structures.Symbol.Companion.Edit
import ink.ui.structures.Symbol.Companion.Error
import ink.ui.structures.Symbol.Companion.Group
import ink.ui.structures.Symbol.Companion.House
import ink.ui.structures.Symbol.Companion.Lock
import ink.ui.structures.Symbol.Companion.LockOpen
import ink.ui.structures.Symbol.Companion.Moon
import ink.ui.structures.Symbol.Companion.Movie
import ink.ui.structures.Symbol.Companion.Person
import ink.ui.structures.Symbol.Companion.Rain
import ink.ui.structures.Symbol.Companion.Snow
import ink.ui.structures.Symbol.Companion.Remove
import ink.ui.structures.Symbol.Companion.Sunshine
import ink.ui.structures.Symbol.Companion.Temperature
import ink.ui.structures.Symbol.Companion.Water
import ink.ui.structures.elements.IconElement

val IconRenderer = renderer<IconElement> {
    val glyph = when (element.symbol) {
        Add -> " + "
        Alarm -> "(!)"
        AlarmOff -> "(\\)"
        ArrowBackward -> " <- "
        ArrowForward -> " -> "
        Bed -> "|Zz|"
        Caution -> "/!\\"
        Close -> " x "
        Cloud -> "(~~)"
        Done -> " OK "
        Edit -> " ## "
        Error -> " !! "
        Group -> " oo "
        House -> "/\\_"
        Lock -> "[≡]"
        LockOpen -> "[ ]"
        Moon -> "( )"
        Movie -> ">>|"
        Person -> " o+ "
        Rain -> "(,,)"
        Snow -> "*.*"
        Remove -> " - "
        Sunshine -> "(#)"
        Temperature -> " T° "
        Water -> " ~~ "
        else -> "███"
    }

    val style = when (element.sentiment) {
        Sentiment.Nominal -> TextStyle(
            color = TextColors.brightWhite.color,
            bgColor = TextColors.gray.color
        )
        Sentiment.Positive -> TextStyle(
            color = TextColors.black.color,
            bgColor = TextColors.brightGreen.color
        )
        Sentiment.Negative -> TextStyle(
            color = TextColors.black.color,
            bgColor = TextColors.brightRed.color
        )
        Sentiment.Primary -> TextStyle(
            color = TextColors.black.color,
            bgColor = TextColors.brightMagenta.color
        )
        Sentiment.Caution -> TextStyle(
            color = TextColors.black.color,
            bgColor = TextColors.brightYellow.color
        )
        Sentiment.Idle -> TextStyle(
            color = TextColors.gray.color,
            bgColor = TextColors.white.color,
        )
    }

    print(style(glyph))
}
