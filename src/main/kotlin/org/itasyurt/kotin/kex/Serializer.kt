package org.itasyurt.kotin.kex

import java.util.*
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.allSupertypes
import kotlin.reflect.full.isSubclassOf
import kotlin.reflect.full.superclasses

/**
 * Created by itasyurt on 12/06/2017.
 */


open abstract class BaseSerializer<T>() {

    val serializedType: KType
        get() = this::class.allSupertypes.find {
            it is KType && BaseSerializer::class.equals(it.classifier)
        }!!.arguments[0].type!!

    init {

        val fields = FieldRegistry.get(this::class)
        if (fields.isEmpty()) {
            initFields(fields)
            FieldRegistry.register(this::class, fields)
        }

    }


    abstract fun initFields(fields: MutableMap<String, Field>)


    open fun serialize(p: T): Map<String, Any?> {

        val actualFields = getRegisteredFields()
        val result = HashMap<String, Any?>()
        actualFields.forEach() {
            (fieldName, field) ->
            result[fieldName] = field.serialize(p as Any)
        }
        return result
    }

    open fun deserialize(map: Map<String, Any?>): T {

        val result = createInstance()
        val actualFields = getRegisteredFields()
        actualFields.forEach() {
            (fieldName, field) ->
            field.deserialize(result as Any, map[fieldName])
        }

        return result;

    }

    private fun createInstance() = (serializedType.classifier as KClass<*>).java.newInstance() as T


    private fun getRegisteredFields(): HashMap<String, Field> {

        var currentClass = this.javaClass.kotlin

        val bsc = BaseSerializer::class.java

        val result = HashMap<String, Field>()
        var serializer_types = get_all_types_of(this, BaseSerializer::class).reversed()
        serializer_types.forEach {
            result.putAll(FieldRegistry.get(it as KClass<BaseSerializer<*>>))
        }

        return result
    }
}

fun get_all_types_of(c: Any, clazz: KClass<*>): List<KClass<*>> {

    val result = ArrayList<KClass<*>>()
    val supers = LinkedList<KClass<*>>()
    var curClass = c.javaClass.kotlin

    supers.add(curClass)
    while (supers.isNotEmpty()) {
        var cur = supers.remove()
        if (cur.isSubclassOf(clazz)) {
            result.add(cur)
            supers.addAll(cur.superclasses)
        }

    }
    return result
}
