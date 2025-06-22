package ink.ui.render.remote

import ink.ui.structures.layouts.UiLayout

/**
 * Sends UI messages to a remote destination.
 *
 * In practice, this isn't *that* different from a Presenter, but every layout
 * should be forwarded to its destination, whereas a presenter is allowed
 * to drop layouts, since only one layout is presented at a time.
 */
interface UiForwarder
{
    suspend fun forwardLayout(layout: UiLayout)
}
