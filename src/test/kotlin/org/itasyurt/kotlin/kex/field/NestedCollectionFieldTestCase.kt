package org.itasyurt.kotlin.kex.field

import org.itasyurt.kotlin.kex.serializer.BaseSerializer
import org.junit.Test
import org.junit.Assert.assertEquals

/**
 * Created by itasyurt on 12/06/2017.
 */

val collectionDummyMap = mapOf("f1" to "v1")

class NestedCollectionFieldTestCase {


    @Test fun testSerialize() {
        val obj = Encapsulating()
        val s1 = SomeNested()
        val s2 = SomeNested()
        obj.nestedCollection = mutableListOf(s1, s2)
        assertEquals(listOf(collectionDummyMap, collectionDummyMap), NestedCollectionField(DummyNestedSerializer::class,
                Encapsulating::nestedCollection).serialize(obj))
    }

    @Test fun testDeserialize() {
        val obj = Encapsulating()

        val value = listOf(collectionDummyMap, collectionDummyMap, collectionDummyMap)
        NestedCollectionField(DummyNestedSerializer::class, Encapsulating::nestedCollection).deserialize(obj, value)
        assertEquals(3, obj.nestedCollection.size)
    }

}

class SomeNested() {
    var name = ""
}

class Encapsulating() {
    var nestedCollection: MutableList<SomeNested> = mutableListOf<SomeNested>()
}

class DummyNestedSerializer() : BaseSerializer<SomeNested>() {

    override fun serialize(p: SomeNested): Map<String, Any?> {
        return dummyMap
    }

    override fun deserialize(map: Map<String, Any?>): SomeNested {
        val result = SomeNested()
        result.name = "dummy"
        return result
    }

    override fun initFields(fields: MutableMap<String, Field>) {

    }

}