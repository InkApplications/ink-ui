package ink.ui.render.remote

import ink.ui.structures.layouts.UiLayout
import ink.ui.structures.render.Presenter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

/**
 * Presents a UI layout by forwarding to a remote destination using a [UiForwarder].
 */
class RemotePresenter(
    private val forwarder: UiForwarder
): Presenter {
    private val queue = MutableSharedFlow<UiLayout>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun presentLayout(layout: UiLayout)
    {
        queue.tryEmit(layout)
    }

    suspend fun bind()
    {
        queue.collectLatest { layout ->
            forwarder.forwardLayout(layout)
        }
    }
}
