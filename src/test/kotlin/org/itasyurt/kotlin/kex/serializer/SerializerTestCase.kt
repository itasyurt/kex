package org.itasyurt.kotlin.kex.serializer

import org.itasyurt.kotlin.kex.field.*
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by itasyurt on 14/06/2017.
 */

class SerializerTestCase {

    @Test fun simpleObjectSerializedSuccessfully() {
        val comment = Comment()
        comment.text = "Hello"
        val serializer = CommentSerializer()
        val data = serializer.serialize(comment)
        assertEquals(mapOf("text" to "Hello"), data)
    }

    @Test fun objectWithMethodFieldSerializedSuccessfully() {
        val comment = Comment()
        comment.text = "Hello"
        val serializer = CommentTextUpperSerializer()
        val data = serializer.serialize(comment)
        assertEquals(mapOf("textUpper" to "HELLO"), data)
    }

    @Test fun objectSerializedWithExtendedSerializerSuccessfully() {
        val comment = Comment()
        comment.text = "Hello"
        val serializer = CommentWithLengthSerializer()
        val data = serializer.serialize(comment)
        assertEquals(mapOf("text" to "Hello", "contentLength" to 5), data)
    }

    @Test fun objectWithNestedSerializedSuccefully() {

        val johndoe = User().apply { username = "johndoe" }
        val comment = Comment().apply {
            text = "Hello"
            owner = johndoe
        }

        val serializer = CommentWithOwnerUserSerializer()
        val data = serializer.serialize(comment)
        assertEquals(mapOf("text" to "Hello", "owner" to mapOf("username" to "johndoe")), data)
    }

    @Test fun objectWithNestedCollectionSerializedSuccefully() {


        val comment1 = Comment().apply {
            text = "Hello"

        }
        val comment2 = Comment().apply {
            text = "Hi"

        }
        val post = Post().apply {
            content = "content"
            comments.addAll(listOf(comment1, comment2))
        }

        val serializer = PostSerializer()
        val data = serializer.serialize(post)
        assertEquals(mapOf("content" to "content", "comments" to listOf(mapOf("text" to "Hello"), mapOf("text" to "Hi"))), data)
    }
}

class Comment() {
    var text: String? = null
    var owner: User? = null
}

class User() {
    var username: String? = null
    var firstName: String? = null
    var lastName: String? = null
}

class Post() {
    var content: String? = null
    var comments = mutableListOf<Comment>()
}

open class CommentSerializer() : BaseSerializer<Comment>() {
    override fun initFields(fields: MutableMap<String, Field>) {
        fields["text"] = SourceField(Comment::text)
    }

}

class CommentWithOwnerUserSerializer() : CommentSerializer() {
    override fun initFields(fields: MutableMap<String, Field>) {
        super.initFields(fields)
        fields["owner"] = NestedRelatedField(OwnerSerializer::class, Comment::owner)
    }
}

class CommentWithLengthSerializer() : CommentSerializer() {
    override fun initFields(fields: MutableMap<String, Field>) {
        super.initFields(fields)
        fields["contentLength"] = MethodField(this::getContentLength)
    }

    fun getContentLength(c: Comment) = c.text?.length

}

class CommentTextUpperSerializer() : BaseSerializer<Comment>() {
    override fun initFields(fields: MutableMap<String, Field>) {
        fields["textUpper"] = MethodField(this::textToUpper)
    }

    private fun textToUpper(c: Comment): Any? {
        return c.text?.toUpperCase()
    }

}

class OwnerSerializer() : BaseSerializer<User>() {
    override fun initFields(fields: MutableMap<String, Field>) {
        fields["username"] = SourceField(User::username)
    }

}

class PostSerializer() : BaseSerializer<Post>() {
    override fun initFields(fields: MutableMap<String, Field>) {
        fields["content"] = MethodField(Post::content)
        fields["comments"] = NestedCollectionField(CommentSerializer::class, Post::comments)
    }
}
