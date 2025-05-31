package ink.ui.render.terminal.renderer

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
    when (element.symbol) {
        Add -> println("\u001B[30;42m + \u001B[0m")
        Alarm -> println("\u001B[30;43m(!)\u001B[0m")
        AlarmOff -> println("\u001B[90;47m(!)\u001B[0m")
        ArrowBackward -> println("\u001B[37;44m <- \u001B[0m")
        ArrowForward -> println("\u001B[37;44m -> \u001B[0m")
        Bed -> println("\u001B[37;45m|Zz|\u001B[0m")
        Caution -> println("\u001B[30;43m/!\\\u001B[0m")
        Close -> println("\u001B[37;41m x \u001B[0m")
        Cloud -> println("\u001B[37;46m(~~)\u001B[0m")
        Done -> println("\u001B[30;42m OK \u001B[0m")
        Edit -> println("\u001B[37;46m ## \u001B[0m")
        Error -> println("\u001B[31;47m !! \u001B[0m")
        Group -> println("\u001B[30;47m oo \u001B[0m")
        House -> println("\u001B[30;43m/\\_\u001B[0m")
        Lock -> println("\u001B[37;42m[=]\u001B[0m")
        LockOpen -> println("\u001B[30;43m[ ]\u001B[0m")
        Moon -> println("\u001B[37;44m( )\u001B[0m")
        Movie -> println("\u001B[37;40m>>|\u001B[0m")
        Person -> println("\u001B[30;47m o+ \u001B[0m")
        Rain -> println("\u001B[34;47m(,,)\u001B[0m")
        Snow -> println("\u001B[37;47m*.*\u001B[0m")
        Remove -> println("\u001B[30;41m - \u001B[0m")
        Sunshine -> println("\u001B[33;40m(#)\u001B[0m")
        Temperature -> println("\u001B[31;47m T~ \u001B[0m")
        Water -> println("\u001B[36;47m ~~ \u001B[0m")
    }
}
