package ink.ui.render.remote

import ink.ui.render.remote.serializer.ButtonElementSerializer
import ink.ui.render.remote.serialization.ElementSerializerConfigContext
import ink.ui.render.remote.serialization.event.EmptyUiEventListener
import ink.ui.render.remote.serialization.event.OnClickEvent
import ink.ui.render.remote.serialization.event.OnContextClickEvent
import ink.ui.render.remote.serialization.event.OnNextValue
import ink.ui.render.remote.serialization.event.OnPreviousValue
import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEventListener
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.render.remote.serialization.event.plus
import ink.ui.render.remote.serializer.CheckBoxElementSerializer
import ink.ui.render.remote.serializer.MenuRowElementSerializer
import ink.ui.render.remote.serializer.SpinnerElementSerializer
import ink.ui.structures.elements.BreadcrumbElement
import ink.ui.structures.elements.DividerElement
import ink.ui.structures.elements.ElementList
import ink.ui.structures.elements.FormattedText
import ink.ui.structures.elements.IconElement
import ink.ui.structures.elements.StatusIndicatorElement
import ink.ui.structures.elements.TextElement
import ink.ui.structures.elements.TextListElement
import ink.ui.structures.elements.ThrobberElement
import ink.ui.structures.elements.UiElement
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
        addElementSerializer(BreadcrumbElement.serializer())
        addElementSerializer(ButtonElementSerializer(uiEvents))
        addElementSerializer(CheckBoxElementSerializer(uiEvents))
        addElementSerializer(DividerElement.serializer())
        addElementSerializer(ElementList.serializer())
        addElementSerializer(FormattedText.serializer())
        addElementSerializer(IconElement.serializer())
        addElementSerializer(MenuRowElementSerializer(uiEvents))
        addElementSerializer(SpinnerElementSerializer(uiEvents))
        addElementSerializer(StatusIndicatorElement.serializer())
        addElementSerializer(TextElement.serializer())
        addElementSerializer(TextListElement.serializer())
        addElementSerializer(ThrobberElement.serializer())

        addEventSerializer(
            subclass = OnClickEvent::class,
            listener = ButtonElementSerializer.Listeners.OnClickListener
                .plus(CheckBoxElementSerializer.Listeners.OnClickListener)
        )
        addEventSerializer(OnContextClickEvent::class, ButtonElementSerializer.Listeners.OnContextClickListener)
        addEventSerializer(OnNextValue::class, SpinnerElementSerializer.Listeners.OnNextValueListener)
        addEventSerializer(OnPreviousValue::class, SpinnerElementSerializer.Listeners.OnPreviousValueListener)
    }
    private val serializer = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        serializersModule = SerializersModule {
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
