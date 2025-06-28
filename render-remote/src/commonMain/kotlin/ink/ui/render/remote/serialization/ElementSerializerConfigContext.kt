package ink.ui.render.remote.serialization

import ink.ui.render.remote.serialization.event.UiEvent
import ink.ui.render.remote.serialization.event.UiEventListener
import ink.ui.render.remote.serialization.event.UiEvents
import ink.ui.structures.elements.UiElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlinx.serialization.serializer
import kotlin.reflect.KClass

/**
 * Container used for configuring custom serializers when using ui forwarding.
 */
data class ElementSerializerConfigContext(
    private val elementModuleBuilder: PolymorphicModuleBuilder<UiElement>,
    private val eventModuleBuilder: PolymorphicModuleBuilder<UiEvent>,
    private val listenerBuilder: (UiEventListener) -> Unit,
    val uiEvents: UiEvents,
) {
    fun <T : UiElement> addElementSerializer(subclass: KClass<T>, serializer: KSerializer<T>)
    {
        elementModuleBuilder.subclass(subclass, serializer)
    }

    inline fun <reified T: UiElement> addElementSerializer(serializer: KSerializer<T>)
    {
        addElementSerializer(T::class, serializer)
    }

    fun <T: UiEvent> addEventSerializer(subclass: KClass<T>, serializer: KSerializer<T>, listener: UiEventListener)
    {
        eventModuleBuilder.subclass(subclass, serializer)
        listenerBuilder(listener)
    }

    inline fun <reified T: UiEvent> addEventSerializer(serializer: KSerializer<T>, listener: UiEventListener)
    {
        addEventSerializer(T::class, serializer, listener)
    }

    inline fun <reified T: UiEvent> addEventSerializer(subclass: KClass<T>, listener: UiEventListener)
    {
        addEventSerializer(subclass, serializer(), listener)
    }
}
