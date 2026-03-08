package ink.ui.render.remote

/**
 * Starts a UI server that receives UI elements and presents them.
 */
interface LayoutServer
{
    suspend fun bind()
}
