package ink.ui.render.remote

import ink.ui.render.remote.serializer.ButtonElementSerializer
import ink.ui.render.remote.serialization.ElementSerializerConfigContext
import ink.ui.render.remote.serialization.LayoutSerializer
import ink.ui.render.remote.serialization.event.EmptyUiEventListener
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.OnContextClickEvent
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEventListener
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.render.remote.serialization.event.plus
import ink.ui.render.remote.serializer.SymbolSerializer
import ink.ui.structures.Symbol
import ink.ui.structures.elements.ButtonElement
import ink.ui.structures.elements.UiElement
import ink.ui.structures.layouts.UiLayout
import ink.ui.structures.render.Presenter
import io.ktor.http.URLProtocol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

class RemoteRenderModule(
    serializationConfig: ElementSerializerConfigContext.() -> Unit = {},
) {
    private val uiEvents: UiEvents = UiEvents()
    private val uiListener: MutableStateFlow<UiEventListener> = MutableStateFlow(EmptyUiEventListener)
    private val allSerializerConfig: ElementSerializerConfigContext.() -> Unit = {
        serializationConfig.invoke(this)
        addElementSerializer(ButtonElement::class, ButtonElementSerializer(uiEvents))
        addEventSerializer(OnClickEvent::class, ButtonElementSerializer.Listeners.OnClickListener)
        addEventSerializer(OnContextClickEvent::class, ButtonElementSerializer.Listeners.OnContextClickListener)
    }
    private val serializer = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
            contextual(UiLayout::class, LayoutSerializer)
            contextual(Symbol::class, SymbolSerializer)
            polymorphic(UiElement::class) elementModule@ {
                polymorphic(UiEvent::class) eventModule@ {
                    this@eventModule.subclass(OnClickEvent::class)
                    val context = ElementSerializerConfigContext(
                        elementModuleBuilder = this@elementModule,
                        eventModuleBuilder = this@eventModule,
                        listenerBuilder = { listener ->
                            uiListener.getAndUpdate { listener + it }
                        },
                        uiEvents = uiEvents,
                    )
                    allSerializerConfig.invoke(context)
                }
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
            uiEventListener = uiListener.value,
            forwardEvents = true,
        )
    }
}
