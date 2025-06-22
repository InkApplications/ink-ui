package ink.ui.render.remote

import ink.ui.render.remote.serialization.LayoutMessage
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.structures.render.Presenter
import io.ktor.http.HttpMethod
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import io.ktor.server.request.receiveText
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.send
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.json.Json

/**
 * Listens on a web port for layout updates and UI events.
 */
internal class WebPresenterServer(
    private val presenter: Presenter,
    private val uiEvents: UiEvents,
    private val port: Int = 8080,
    private val serializer: Json,
): LayoutServer {
    override suspend fun bind()
    {
        embeddedServer(CIO, port) {
            install(WebSockets.Plugin)
            routing {
                route("/layout", method = HttpMethod.Companion.Put) {
                    handle {
                        val layout = try {
                            serializer.decodeFromString<LayoutMessage>(call.receiveText())
                        } catch (e: Throwable) {
                            e.printStackTrace()
                            throw e
                        }
                        presenter.presentLayout(layout.layout!!)
                    }
                }
                webSocket("/events") {
                    try {
                        uiEvents.events.collect {
                            send(serializer.encodeToString(PolymorphicSerializer(UiEvent::class), it))
                        }
                    } catch (e: Throwable) {
                        e.printStackTrace()
                        throw e
                    }
                }
            }
        }.startSuspend(wait = true)
    }
}
