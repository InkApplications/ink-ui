package ink.ui.render.remote

import ink.ui.render.remote.serialization.ElementId
import ink.ui.render.remote.serialization.LayoutMessage
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.OnContextClickEvent
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLBuilder
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

/**
 * Forwards UI events via HTTP requests and websockets.
 */
internal class WebForwarder(
    private val uiEvents: UiEvents,
    private val serializer: Json,
    private val host: String,
    private val port: Int = 8080,
    private val basePath: String? = null,
    private val protocol: URLProtocol = URLProtocol.Companion.HTTPS,
    private val forwardEvents: Boolean = true,
): UiForwarder {
    private val baseUrl = URLBuilder(
        protocol = protocol,
        host = host,
        port = port,
    ).apply {
        if (basePath != null) {
            appendPathSegments(basePath)
        }
    }.build()
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(serializer)
        }
        install(WebSockets)
    }

    override suspend fun forwardLayout(layout: UiLayout)
    {
        val message = LayoutMessage(
            layout = layout,
        )
        val pairs = MutableStateFlow<Map<ElementId, UiElement>>(emptyMap())
        coroutineScope {
            launch {
                uiEvents.mappings.consumeEach { event ->
                    pairs.update { currentMap ->
                        currentMap + (event.first to event.second)
                    }
                }
            }


            httpClient.put(URLBuilder(baseUrl).appendPathSegments("layout").build()) {
                contentType(ContentType.Application.Json)
                setBody(message)
            }

            launch {
                bindCallbacks { uiEvent ->
                    when (uiEvent) {
                        is OnClickEvent -> {
                            (pairs.value[uiEvent.id] as? ButtonElement)?.onClick()
                        }
                        is OnContextClickEvent -> {
                            (pairs.value[uiEvent.id] as? ButtonElement)?.onContextClick?.invoke()
                        }
                    }
                }
            }
        }

    }

    private suspend fun bindCallbacks(onEvent: (UiEvent) -> Unit)
    {
        if (!forwardEvents) return
        httpClient.webSocket(
            host = baseUrl.host,
            port = baseUrl.port,
            path = "${baseUrl.encodedPath}/events",
        ) {
            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    val event = serializer.decodeFromString<UiEvent>(frame.readText())
                    onEvent(event)
                }
            }
        }
    }
}
