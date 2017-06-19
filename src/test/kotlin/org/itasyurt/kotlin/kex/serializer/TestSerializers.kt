package org.itasyurt.kotlin.kex.serializer

import org.itasyurt.kotlin.kex.field.*

/**
 * Created by itasyurt on 19/06/2017.
 */



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
        fields["content"] = SourceField(Post::content)
        fields["comments"] = NestedCollectionField(CommentSerializer::class, Post::comments)
    }
}

