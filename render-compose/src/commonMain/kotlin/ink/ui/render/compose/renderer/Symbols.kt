package ink.ui.render.compose.renderer

import com.inkapplications.ui.render_compose.generated.resources.*
import ink.ui.structures.Symbol
import org.jetbrains.compose.resources.DrawableResource

/**
 * Map symbols to their drawable resources.
 */
val Symbol.resource: DrawableResource get() = when (this) {
    Symbol.Add -> Res.drawable.add
    Symbol.Alarm -> Res.drawable.alarm
    Symbol.AlarmOff -> Res.drawable.alarm_off
    Symbol.ArrowBackward -> Res.drawable.arrow_backward
    Symbol.ArrowForward -> Res.drawable.arrow_forward
    Symbol.Bed -> Res.drawable.bed
    Symbol.Caution -> Res.drawable.caution
    Symbol.Close -> Res.drawable.close
    Symbol.Cloud -> Res.drawable.cloud
    Symbol.Done -> Res.drawable.done
    Symbol.Edit -> Res.drawable.edit
    Symbol.Error -> Res.drawable.error
    Symbol.Group -> Res.drawable.group
    Symbol.House -> Res.drawable.house
    Symbol.Lock -> Res.drawable.lock
    Symbol.LockOpen -> Res.drawable.lock_open
    Symbol.Moon -> Res.drawable.moon
    Symbol.Movie -> Res.drawable.movie
    Symbol.Person -> Res.drawable.person
    Symbol.Rain -> Res.drawable.rain
    Symbol.Remove -> Res.drawable.remove
    Symbol.Snow -> Res.drawable.snow
    Symbol.Sunshine -> Res.drawable.sunshine
    Symbol.Temperature -> Res.drawable.temperature
    Symbol.Water -> Res.drawable.water
    else -> Res.drawable.unknown
}
