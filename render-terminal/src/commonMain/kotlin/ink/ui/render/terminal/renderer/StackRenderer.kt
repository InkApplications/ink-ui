package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.StackElement
import kotlinx.coroutines.CancellationException

val StackRenderer = renderer<StackElement> {
    try {
        element.queue.collect { element ->
            parent.render(element, parent)
        }
    } catch (e: CancellationException) {
        element.close()
        throw e
    }
}
