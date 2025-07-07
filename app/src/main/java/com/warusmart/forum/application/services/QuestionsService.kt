package com.warusmart.forum.application.services

import android.util.Log
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.forum.domain.model.Question
import com.warusmart.forum.domain.model.QuestionPost
import com.warusmart.forum.domain.model.QuestionPut
import com.warusmart.forum.infrastructure.ForumApi
import io.github.cdimascio.dotenv.dotenv
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketException

/**
 * Service for handling forum questions, including CRUD operations and API communication.
 */
class QuestionsService {
    val dotenv = dotenv(){
        directory = "/assets"
        filename = "env"
    }

    private val api: ForumApi
    private val token = SessionManager.token

    init {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder: Request.Builder = original.newBuilder()
                .header("Authorization", "Bearer $token")
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(dotenv["API_URL"])
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ForumApi::class.java)
    }

    /**
     * Retrieves all questions from the API.
     */
    suspend fun getAllQuestions(): List<Question> {
        return try {
            val questions = api.getQuestions()
            Log.d("QuestionsService","Raw JSON response: $questions")
            questions
        } catch (e: SocketException){
            Log.e("QuestionsService", "Error: ${e.message}")
            emptyList()
        }
    }

    /**
     * Retrieves all questions by a specific author from the API.
     */
    suspend fun getQuestionsByAuthorId(authorId: Int): List<Question> {
        return try {
            val questions = api.getQuestionsByAuthorId(authorId)
            Log.d("QuestionsService","Raw JSON response: $questions")
            questions
        } catch (e: SocketException){
            Log.e("QuestionsService", "Error: ${e.message}")
            emptyList()
        }
    }

    /**
     * Adds a new question using the API.
     */
    suspend fun addQuestion(question: QuestionPost) {
        try {
            api.addQuestion(question)
            Log.d("QuestionsService", "Question added successfully")
        } catch (e: Exception) {
            Log.e("QuestionsService", "Error: ${e.message}")
        }
    }

    /**
     * Updates an existing question using the API.
     */
    suspend fun updateQuestion(questionId: Int, question: QuestionPut) {
        try {
            api.updateQuestion(questionId, question)
            Log.d("QuestionsService", "Question updated successfully")
        } catch (e: Exception) {
            Log.e("QuestionsService", "Error: ${e.message}")
        }
    }

    /**
     * Deletes a question using the API.
     */
    suspend fun deleteQuestion(questionId: Int) {
        try {
            api.deleteQuestion(questionId)
            Log.d("QuestionsService", "Question deleted successfully")
        } catch (e: Exception) {
            Log.e("QuestionsService", "Error: ${e.message}")
        }
    }
}