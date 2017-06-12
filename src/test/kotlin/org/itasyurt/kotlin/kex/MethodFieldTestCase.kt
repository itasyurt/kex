package org.itasyurt.kotlin.kex

import org.junit.Assert
import org.junit.Test

/**
 * Created by itasyurt on 12/06/2017.
 */
class MethodFieldTestCase {

    @Test fun testSerialize() {
        class SomeClass() {
            var text = ""
        }
        fun toUpperFunction(text:String)=text.toUpperCase()

        val someObject = SomeClass()
        someObject.text = "Text"
        Assert.assertEquals("TEXT", MethodField(::toUpperFunction).serialize("text"))
    }
}
