package com.example.warusmart.forum.domain.model

import java.io.Serializable

/**{
 "questionId": 1,
 "authorId": 1,
 "categoryId": 1,
 "questionText": "string",
 "date": "2024-09-19T14:49:38.038"
 }
 */
class Question(
        var questionId: Int,
        var authorId: Int,
        var categoryId: Int,
        var questionText: String,
        var date: String
) : Serializable {

}

class QuestionPost(
        var authorId: Int,
        var categoryId: Int,
        var questionText: String,
        var date: String
) : Serializable {

}

class QuestionPut(
        var categoryId: Int,
        var questionText: String
) : Serializable {

}