package com.warusmart.forum.domain.model

import java.io.Serializable

/**{
 "questionId": 1,
 "authorId": 1,
 "categoryId": 1,
 "questionText": "string",
 "date": "2024-09-19T14:49:38.038"
 }
 */
/**
 * Model representing a question in the forum.
 */
class Question(
        var questionId: Int,
        var authorId: Int,
        var categoryId: Int,
        var questionText: String,
        var date: String
) : Serializable {
}

/**
 * Model for creating a new question in the forum.
 */
class QuestionPost(
        var authorId: Int,
        var categoryId: Int,
        var questionText: String,
        var date: String
) : Serializable {
}

/**
 * Model for updating an existing question in the forum.
 */
class QuestionPut(
        var categoryId: Int,
        var questionText: String
) : Serializable {

}