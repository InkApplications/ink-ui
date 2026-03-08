package ink.ui.render.terminal.renderer

import ink.ui.structures.elements.StackElement
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.distinctUntilChanged

val StackRenderer = renderer<StackElement> {
    try {
        element.queue
            .let { if (element.requireDistinct) it.distinctUntilChanged() else it }
            .collect { element ->
                parent.render(element, parent)
                print("\n")
            }
    } catch (e: CancellationException) {
        element.close()
        throw e
    }
}
