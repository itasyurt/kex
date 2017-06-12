package org.itasyurt.kotlin.kex

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by itasyurt on 12/06/2017.
 */

val dummyMap = mapOf("f1" to "v1")

class NestedRelatedFieldTestCase {

    @Test fun testSerialize() {
        val obj = SomeOther()
        val nested = Nested()
        nested.name = "Hoba"
        obj.nested = nested

        assertEquals(dummyMap, NestedRelatedField(DummySerializer::class, SomeOther::nested).serialize(obj))
    }

    @Test fun testDeserialize() {
        val someObj = SomeOther()

        val valueMap = emptyMap<String,String>()
        val nested = NestedRelatedField(DummySerializer::class, SomeOther::nested).deserialize(someObj,valueMap)
        assertEquals("dummy",someObj.nested!!.name)
    }
}


class Nested() {
    var name = ""
}

class SomeOther() {
    var nested: Nested? = null
}

class DummySerializer() : BaseSerializer<Nested>() {

    override fun serialize(p: Nested): Map<String, Any?> {
        return dummyMap
    }

    override fun deserialize(map: Map<String, Any?>): Nested {
        val result = Nested()
        result.name = "dummy"
        return result
    }

    override fun initFields(fields: MutableMap<String, Field>) {

    }

}

