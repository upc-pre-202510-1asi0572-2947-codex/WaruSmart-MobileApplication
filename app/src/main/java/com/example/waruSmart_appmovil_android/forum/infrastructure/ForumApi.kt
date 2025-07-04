package com.example.waruSmart_appmovil_android.forum.infrastructure

import com.example.waruSmart_appmovil_android.forum.domain.model.Answer
import com.example.waruSmart_appmovil_android.forum.domain.model.AnswerPost
import com.example.waruSmart_appmovil_android.forum.domain.model.Category
import com.example.waruSmart_appmovil_android.forum.domain.model.Question
import com.example.waruSmart_appmovil_android.forum.domain.model.QuestionPost
import com.example.waruSmart_appmovil_android.forum.domain.model.QuestionPut
import com.example.waruSmart_appmovil_android.iam.domain.model.ProfileResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ForumApi {
    //Questions
    @GET("forum/questions")
    suspend fun getQuestions(): List<Question>

    @GET("forum/questions/author/{authorId}")
    suspend fun getQuestionsByAuthorId(@Path("authorId") authorId: Int): List<Question>

    @POST("forum/questions")
    suspend fun addQuestion(@Body question: QuestionPost)

    @PUT("forum/questions/{questionId}")
    suspend fun updateQuestion(@Path("questionId") questionId: Int, @Body question: QuestionPut)

    @DELETE("forum/questions/{questionId}")
    suspend fun deleteQuestion(@Path("questionId") questionId: Int)

    //Answers
    @GET("forum/question/{questionId}/answers")
    suspend fun getAnswersByQuestionId(@Path("questionId") questionId: Int): List<Answer>

    @POST("forum/answers")
    suspend fun addAnswer(@Body answer: AnswerPost)

    //Categories
    @GET("forum/categories")
    suspend fun getCategories(): List<Category>

    @GET("forum/categories/{categoryId}")
    suspend fun getCategoryById(@Path("categoryId") categoryId: Int): Category

    //Profile
    @GET("profiles/{profileId}")
    suspend fun getProfileById(@Path("profileId") profileId: Int) : ProfileResponse

    @GET("profiles")
    suspend fun getAllProfiles(): List<ProfileResponse>
}