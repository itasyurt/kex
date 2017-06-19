package org.itasyurt.kotlin.kex.serializer

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by itasyurt on 19/06/2017.
 */
class DeserializerTestCase {

    @Test fun simpleObjectDeserializedSuccessfully() {

        val serializer = CommentSerializer()
        val comment = serializer.deserialize(mapOf("text" to "Hello"))
        assertEquals("Hello", comment.text)
    }

    @Test fun objectDeserializedWithExtendedSerializerSuccessfully() {

        val serializer = CommentWithLengthSerializer()
        val comment = serializer.deserialize(mapOf("text" to "Hello", "contentLength" to 5))
        assertEquals(comment.text, "Hello")
    }

    @Test fun objectWithNestedDeserializedSuccefully() {


        val data = mapOf("text" to "Hello", "owner" to mapOf("username" to "johndoe"))

        val serializer = CommentWithOwnerUserSerializer()
        val comment = serializer.deserialize(data)
        assertEquals("Hello", comment.text)
        assertEquals("johndoe", comment.owner?.username)
    }


    @Test fun objectWithNestedCollectionSerializedSuccefully() {

        val serializer = PostSerializer()
        val data = mapOf("content" to "content", "comments" to listOf(mapOf("text" to "Hello"), mapOf("text" to "Hi")))
        val post = serializer.deserialize(data)
        assertEquals("content", post.content)
        assertEquals(2, post.comments.size)
        assertEquals("Hello", post.comments[0].text)
    }
}
