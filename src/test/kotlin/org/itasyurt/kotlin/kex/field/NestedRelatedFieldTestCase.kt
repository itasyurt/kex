package org.itasyurt.kotlin.kex.field

import org.itasyurt.kotlin.kex.serializer.BaseSerializer
import org.junit.Assert.assertEquals

/**
 * Created by itasyurt on 12/06/2017.
 */

val dummyMap = mapOf("f1" to "v1")

class NestedRelatedFieldTestCase {

    @org.junit.Test fun testSerialize() {
        val obj = org.itasyurt.kotlin.kex.field.SomeOther()
        val nested = org.itasyurt.kotlin.kex.field.Nested()
        nested.name = "Hoba"
        obj.nested = nested

        assertEquals(org.itasyurt.kotlin.kex.field.dummyMap, NestedRelatedField(DummySerializer::class, SomeOther::nested).serialize(obj))
    }

    @org.junit.Test fun testDeserialize() {
        val someObj = org.itasyurt.kotlin.kex.field.SomeOther()

        val valueMap = emptyMap<String,String>()
        val nested = NestedRelatedField(DummySerializer::class, SomeOther::nested).deserialize(someObj,valueMap)
        assertEquals("dummy",someObj.nested!!.name)
    }
}


class Nested() {
    var name = ""
}

class SomeOther() {
    var nested: org.itasyurt.kotlin.kex.field.Nested? = null
}

class DummySerializer() : BaseSerializer<Nested>() {

    override fun serialize(p: org.itasyurt.kotlin.kex.field.Nested): Map<String, Any?> {
        return org.itasyurt.kotlin.kex.field.dummyMap
    }

    override fun deserialize(map: Map<String, Any?>): org.itasyurt.kotlin.kex.field.Nested {
        val result = org.itasyurt.kotlin.kex.field.Nested()
        result.name = "dummy"
        return result
    }

    override fun initFields(fields: MutableMap<String, Field>) {

    }

}

