package org.itasyurt.kotin.kex

import kotlin.reflect.KClass

/**
 * Created by itasyurt on 12/06/2017.
 */
object FieldRegistry {
    val serializerFieldsMap = mutableMapOf<KClass<out BaseSerializer <*>>, Map<String, Field>>().withDefault {
        mapOf<String, Field>()
    }

    open fun register(serializer: KClass<out BaseSerializer<*>>, fields: HashMap<String, Field>) {
        serializerFieldsMap[serializer] = fields
    }

    open fun get(serializer: KClass<out BaseSerializer<*>>): HashMap<String, Field> {
        return HashMap(serializerFieldsMap.getValue(serializer))
    }
}