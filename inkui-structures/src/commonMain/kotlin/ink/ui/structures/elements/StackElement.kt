package ink.ui.structures.elements

import ink.ui.structures.TextStyle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

/**
 * Element that buffers UI elements into its own layout dynamically.
 */
data class StackElement(
    val requireDistinct: Boolean = true,
): UiElement.Interactive {
    private val renderRequests = Channel<UiElement>()
    val queue: Flow<UiElement> = renderRequests.consumeAsFlow()

    suspend fun print(element: UiElement)
    {
        renderRequests.send(element)
    }

    suspend fun close()
    {
        renderRequests.close()
        suspendCancellableCoroutine { continuation ->
            renderRequests.invokeOnClose {
                continuation.resume(Unit)
            }
        }
    }
}

suspend fun StackElement.println(
    message: String,
    textStyle: TextStyle = TextStyle.Body
) {
    print(TextElement(message, textStyle))
}
