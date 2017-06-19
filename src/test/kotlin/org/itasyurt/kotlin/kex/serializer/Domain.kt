package org.itasyurt.kotlin.kex.serializer

/**
 * Created by itasyurt on 19/06/2017.
 */
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