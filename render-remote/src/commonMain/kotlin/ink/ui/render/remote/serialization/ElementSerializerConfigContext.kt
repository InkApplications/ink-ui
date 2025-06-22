package ink.ui.render.remote.serialization

import ink.ui.structures.elements.UiElement
import kotlinx.serialization.KSerializer
import kotlinx.serialization.modules.PolymorphicModuleBuilder
import kotlin.reflect.KClass

/**
 * Container used for configuring custom serializers when using ui forwarding.
 */
data class ElementSerializerConfigContext(
    private val moduleBuilder: PolymorphicModuleBuilder<UiElement>,
    val uiEvents: UiEvents,
) {
    fun <T : UiElement> addSerializer(subclass: KClass<T>, serializer: KSerializer<T>)
    {
        moduleBuilder.subclass(subclass, serializer)
    }

    inline fun <reified T: UiElement> addSerializer(serializer: KSerializer<T>)
    {
        addSerializer(T::class, serializer)
    }
}
