package ink.ui.render.remote

import ink.ui.render.remote.serializer.ButtonElementSerializer
import ink.ui.render.remote.serialization.ElementSerializerConfigContext
import ink.ui.render.remote.serialization.LayoutSerializer
import ink.ui.render.remote.serialization.event.CompositeEventListener
import ink.ui.render.remote.serialization.event.EmptyUiEventListener
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEventListener
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.render.remote.serialization.event.plus
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import ink.ui.structures.render.Presenter
import io.ktor.http.URLProtocol
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

class RemoteRenderModule(
    serializerConfig: ElementSerializerConfigContext.() -> Unit = {},
    private val uiEventListener: UiEventListener = EmptyUiEventListener,
) {
    private val uiEvents: UiEvents = UiEvents()
    private val builtInListener = CompositeEventListener(
        listOf(
            ButtonElementSerializer.Listeners.OnClickListener,
            ButtonElementSerializer.Listeners.OnContextClickListener,
        )
    )
    private val allSerializerConfig: ElementSerializerConfigContext.() -> Unit = {
        serializerConfig.invoke(this)
        addSerializer(ButtonElement::class, ButtonElementSerializer(uiEvents))
    }
    private val serializer = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            contextual(UiLayout::class, LayoutSerializer)
            polymorphic(UiElement::class) {
                val context = ElementSerializerConfigContext(
                    moduleBuilder = this,
                    uiEvents = uiEvents,
                )
                allSerializerConfig.invoke(context)
            }
            polymorphic(UiEvent::class) {
                subclass(OnClickEvent::class)
            }
        }
    }

    fun createServer(
        presenter: Presenter,
        port: Int = 8080,
    ): LayoutServer {
        return WebPresenterServer(
            presenter = presenter,
            uiEvents = uiEvents,
            port = port,
            serializer = serializer,
        )
    }

    fun createForwarder(
        host: String,
        port: Int = 8080,
        basePath: String? = null,
        protocol: URLProtocol = URLProtocol.Companion.HTTPS,
    ): UiForwarder {
        return WebForwarder(
            host = host,
            port = port,
            basePath = basePath,
            protocol = protocol,
            serializer = serializer,
            uiEvents = uiEvents,
            uiEventListener = uiEventListener + builtInListener,
            forwardEvents = true,
        )
    }
}
