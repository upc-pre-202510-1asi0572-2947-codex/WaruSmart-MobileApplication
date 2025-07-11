package com.warusmart.forum.infrastructure

import com.warusmart.forum.domain.model.Answer
import com.warusmart.forum.domain.model.AnswerPost
import com.warusmart.forum.domain.model.Category
import com.warusmart.forum.domain.model.Question
import com.warusmart.forum.domain.model.QuestionPost
import com.warusmart.forum.domain.model.QuestionPut
import com.warusmart.iam.domain.model.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

/**
 * Retrofit API interface for forum-related endpoints, including questions, answers, categories, and profiles.
 */
interface ForumApi {
    /**
     * Gets all questions from the forum.
     */
    @GET("forum/questions")
    suspend fun getQuestions(): List<Question>

    /**
     * Gets all questions by a specific author.
     */
    @GET("forum/questions/author/{authorId}")
    suspend fun getQuestionsByAuthorId(@Path("authorId") authorId: Int): List<Question>

    /**
     * Adds a new question to the forum.
     */
    @POST("forum/questions")
    suspend fun addQuestion(@Body question: QuestionPost)

    /**
     * Updates an existing question in the forum.
     */
    @PUT("forum/questions/{questionId}")
    suspend fun updateQuestion(@Path("questionId") questionId: Int, @Body question: QuestionPut)

    /**
     * Deletes a question from the forum.
     */
    @DELETE("forum/questions/{questionId}")
    suspend fun deleteQuestion(@Path("questionId") questionId: Int)

    /**
     * Gets all answers for a specific question.
     */
    @GET("forum/question/{questionId}/answers")
    suspend fun getAnswersByQuestionId(@Path("questionId") questionId: Int): List<Answer>

    /**
     * Adds a new answer to a question.
     */
    @POST("forum/answers")
    suspend fun addAnswer(@Body answer: AnswerPost)

    /**
     * Gets all categories from the forum.
     */
    @GET("forum/categories")
    suspend fun getCategories(): List<Category>

    /**
     * Gets a category by its ID.
     */
    @GET("forum/categories/{categoryId}")
    suspend fun getCategoryById(@Path("categoryId") categoryId: Int): Category

    /**
     * Gets a user profile by its ID.
     */
    @GET("profiles/{profileId}")
    suspend fun getProfileById(@Path("profileId") profileId: Int) : ProfileResponse

    /**
     * Gets all user profiles.
     */
    @GET("profiles")
    suspend fun getAllProfiles(): List<ProfileResponse>
}