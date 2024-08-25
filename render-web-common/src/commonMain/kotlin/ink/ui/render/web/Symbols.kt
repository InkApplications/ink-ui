package ink.ui.render.web

import ink.ui.structures.Symbol

const val RESOURCE_SVG_PATH = "composeResources/com.inkapplications.ui.render_web_common.generated.resources/svg"

val Symbol.svgSrc: String get() = when (this) {
    Symbol.Add -> "add.svg"
    Symbol.Alarm -> "alarm.svg"
    Symbol.AlarmOff -> "alarm_off.svg"
    Symbol.ArrowBackward -> "arrow_backward.svg"
    Symbol.ArrowForward -> "arrow_forward.svg"
    Symbol.Bed -> "bed.svg"
    Symbol.Caution -> "caution.svg"
    Symbol.Close -> "close.svg"
    Symbol.Cloud -> "cloud.svg"
    Symbol.Done -> "done.svg"
    Symbol.Edit -> "edit.svg"
    Symbol.Error -> "error.svg"
    Symbol.Group -> "group.svg"
    Symbol.House -> "house.svg"
    Symbol.Lock -> "lock.svg"
    Symbol.LockOpen -> "lock_open.svg"
    Symbol.Moon -> "moon.svg"
    Symbol.Movie -> "movie.svg"
    Symbol.Person -> "person.svg"
    Symbol.Rain -> "rain.svg"
    Symbol.Remove -> "remove.svg"
    Symbol.Sunshine -> "sunshine.svg"
    Symbol.Temperature -> "temperature.svg"
    Symbol.Water -> "water.svg"
    else -> "unknown.svg"
}.let { "$RESOURCE_SVG_PATH/$it" }
