package org.itasyurt.kotlin.kex.field

import org.itasyurt.kotlin.kex.serializer.BaseSerializer
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.primaryConstructor

/**
 * Created by itasyurt on 12/06/2017.
 */
abstract class Field {

    abstract fun serialize(obj: Any): Any?
    abstract fun deserialize(obj: Any, value: Any?)

}


class SourceField<T, R>(val prop: KMutableProperty1<T, R>) : Field() {

    override fun deserialize(obj: Any, value: Any?) {
        obj as T
        prop.set(obj, value as R)
    }


    override fun serialize(obj: Any): Any? {
        return prop.get(obj as T)
    }


}

class MethodField<T>(val f: (T) -> Any?) : Field() {
    override fun deserialize(obj: Any, value: Any?) {

    }

    override fun serialize(obj: Any): Any? {
        return f(obj as T)
    }

}

class NestedRelatedField<R, T>(val serializerClass: KClass<out BaseSerializer<T>>,
                               val prop: KMutableProperty1<R, T?>) : Field() {
    override fun deserialize(obj: Any, value: Any?) {
        obj as R
        val fieldValue = serializerClass.primaryConstructor!!.call().deserialize(value as Map<String, Any>)
        prop.set(obj, fieldValue)

    }


    override fun serialize(obj: Any): Any? {

        return serializerClass.primaryConstructor!!.call().serialize(prop.get(obj as R)!!)


    }


}

class NestedCollectionField<R, T>(val serializerClass: KClass<out BaseSerializer<T>>,
                                  val prop: KMutableProperty1<R, out MutableCollection<T>>) : Field() {
    override fun deserialize(obj: Any, value: Any?) {
        obj as R
        value as List<Map<String, Any>>
        val fieldValue = value.map { serializerClass.primaryConstructor!!.call().deserialize(it) }
        prop.get(obj).addAll(fieldValue)
    }

    override fun serialize(obj: Any): Any? {

        val values = prop.get(obj as R)!!
        values as Collection<T>

        return values.map { serializerClass.primaryConstructor!!.call().serialize(it) }


    }


}