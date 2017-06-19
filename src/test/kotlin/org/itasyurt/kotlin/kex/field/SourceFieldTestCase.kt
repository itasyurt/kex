package org.itasyurt.kotlin.kex.field

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by itasyurt on 12/06/2017.
 */



class SourceFieldTestCase {

    @Test fun testSourceFieldSerialize() {
        class SomeClass() {
            var text = ""
        }

        val someObject = SomeClass()
        someObject.text = "Text"
        assertEquals("Text", SourceField(SomeClass::text).serialize(someObject))
    }

    @Test fun testSourceFieldDeserialize() {
        class SomeClass() {
            var text = ""
        }

        val someObject = SomeClass()

        SourceField(SomeClass::text).deserialize(someObject, "SomeText")
        assertEquals("SomeText", someObject.text)
    }
}

