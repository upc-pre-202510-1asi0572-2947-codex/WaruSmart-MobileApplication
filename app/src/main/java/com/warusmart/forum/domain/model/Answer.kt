package com.warusmart.forum.domain.model

/**
 * {
 *   "answerId": 1,
 *   "authorId": 1,
 *   "questionId": 1,
 *   "answerText": "string"
 * }
 */
/**
 * Model representing an answer in the forum.
 */
class Answer(
        var answerId: Int,
        var authorId: Int,
        var questionId: Int,
        var answerText: String
) {
}

/**
 * Model for creating a new answer in the forum.
 */
class AnswerPost(
        var authorId: Int,
        var questionId: Int,
        var answerText: String
) {

}