package ink.ui.structures

import kotlin.jvm.JvmInline

/**
 * A symbol used for iconography in the UI.
 *
 * This is an unstable collection, so unknown symbols should be handled by
 * the renderer gracefully.
 */
@JvmInline
value class Symbol(val key: String) {
    companion object {
        val Add = Symbol("Add")
        val Alarm = Symbol("Alarm")
        val AlarmOff = Symbol("AlarmOff")
        val ArrowBackward = Symbol("ArrowBackward")
        val ArrowForward = Symbol("ArrowForward")
        val Bed = Symbol("Bed")
        val Caution = Symbol("Caution")
        val Close = Symbol("Close")
        val Cloud = Symbol("Cloud")
        val Done = Symbol("Done")
        val Edit = Symbol("Edit")
        val Error = Symbol("Error")
        val Group = Symbol("Group")
        val House = Symbol("House")
        val Lock = Symbol("Lock")
        val LockOpen = Symbol("LockOpen")
        val Moon = Symbol("Moon")
        val Movie = Symbol("Movie")
        val Person = Symbol("Person")
        val Rain = Symbol("Rain")
        val Remove = Symbol("Remove")
        val Sunshine = Symbol("Sunshine")
        val Temperature = Symbol("Temperature")
        val Water = Symbol("Water")
    }
}
