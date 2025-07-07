package com.warusmart.forum.interfaces.activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.shared.interfaces.BaseActivity
import com.warusmart.R
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.forum.interfaces.adapters.AdapterAnswer
import com.warusmart.forum.application.services.AnswersService
import com.warusmart.forum.domain.model.Answer
import com.warusmart.forum.domain.model.AnswerPost
import com.warusmart.forum.domain.model.Question
import com.warusmart.forum.application.services.CategoriesService
import com.warusmart.forum.application.services.ProfileServiceForum
import com.warusmart.iam.domain.model.ProfileResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity that displays the details of a question and allows viewing and adding answers.
 * Manages the display of the question, its answers, and the dialog to add a new answer.
 */
class AnswersActivity : BaseActivity() {

    private lateinit var answersService: AnswersService
    private lateinit var categoriesService: CategoriesService
    private lateinit var profileService: ProfileServiceForum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Initializes the view and required services for answers and profiles.
         */
        layoutInflater.inflate(R.layout.activity_answers, findViewById(R.id.container))
        enableEdgeToEdge()

        answersService = AnswersService()
        categoriesService = CategoriesService()
        profileService = ProfileServiceForum()

        val question = intent.getSerializableExtra("question") as Question
        val isFromCommunity = intent.getBooleanExtra("isFromCommunity", false)
        val profileId = SessionManager.profileId

        setupBackButton()
        displayQuestionDetails(question, profileId)
        fetchAndDisplayAnswers(question.questionId, profileId)
        setupAddAnswerButton(question, isFromCommunity, profileId)
    }

    override fun onResume() {
        super.onResume()
        /**
         * Marks the forum tab as selected in the bottom navigation.
         */
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_forum
    }

    /**
     * Displays the details of the selected question.
     */
    private fun displayQuestionDetails(question: Question, profileId: Int) {
        // Muestra los detalles de la pregunta seleccionada
        val txtQuestionAnswers: TextView = findViewById(R.id.txtQuestionAnswers)
        val txtUserQuestion: TextView = findViewById(R.id.txtUserQuestion)
        val txtDateQuestion: TextView = findViewById(R.id.txtDateQuestion)
        val txtCategoryQuestion: TextView = findViewById(R.id.txtCategoryQuestion)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val category = categoriesService.getCategoryById(question.categoryId)
                val profile = profileService.getProfileById(question.authorId)
                withContext(Dispatchers.Main) {
                    txtCategoryQuestion.text = category.name
                    txtUserQuestion.text = profile?.fullName ?: "Unknown"
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("AnswersActivity", "Error: ${e.message}")
                }
            }
        }

        txtQuestionAnswers.text = question.questionText
        txtDateQuestion.text = question.date.toString().take(10)
    }

    /**
     * Sets up the button to add an answer if the question is from the community.
     */
    private fun setupAddAnswerButton(question: Question, isFromCommunity: Boolean, profileId: Int) {
        // Configura el botón para agregar una respuesta si la pregunta es de la comunidad
        val addQuestionButton: Button = findViewById(R.id.btnAddAnswer)

        if (isFromCommunity) {
            addQuestionButton.visibility = View.VISIBLE
        } else {
            addQuestionButton.visibility = View.GONE
        }

        addQuestionButton.setOnClickListener {
            showAddAnswerDialog(question, profileId)
        }
    }

    /**
     * Shows a dialog to add a new answer.
     */
    private fun showAddAnswerDialog(question: Question, profileId: Int) {
        // Muestra un diálogo para agregar una nueva respuesta
        val dialogView = layoutInflater.inflate(R.layout.add_answer_dialog, null)
        val answerDialogTitle: TextView = dialogView.findViewById(R.id.answerDialogTitle)
        val editTextAnswer: EditText = dialogView.findViewById(R.id.editTextAnswer)
        val btnAddAnswer: Button = dialogView.findViewById(R.id.btnAddAnswer)
        val btnCancelAnswer: Button = dialogView.findViewById(R.id.btnCancelAnswer)

        answerDialogTitle.text = "Add Answer"
        btnAddAnswer.text = "Add"

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        btnAddAnswer.setOnClickListener {
            val answerText = editTextAnswer.text.toString()

            if (answerText.isBlank()) {
                Toast.makeText(this, "Please enter the answer content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val answer = AnswerPost(
                questionId = question.questionId,
                answerText = answerText,
                authorId = profileId
            )
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    answersService.addAnswer(answer)
                    withContext(Dispatchers.Main) {
                        fetchAndDisplayAnswers(question.questionId, profileId)
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Log.e("AnswersActivity", "Error: ${e.message}")
                    }
                }
            }
            dialog.dismiss()
        }

        btnCancelAnswer.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * Fetches and displays all answers associated with a question.
     */
    private fun fetchAndDisplayAnswers(questionId: Int, profileId: Int) {
        // Obtiene y muestra todas las respuestas asociadas a una pregunta
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val answers = answersService.getAllAnswersByQuestionId(questionId)
                val profiles = profileService.getAllProfiles()
                if (profiles != null) {
                    withContext(Dispatchers.Main) {
                        displayAnswers(answers, profiles)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("AnswersActivity", "Error: ${e.message}")
                }
            }
        }
    }

    /**
     * Displays the answers in the RecyclerView.
     */
    private fun displayAnswers(answers: List<Answer>, profiles: List<ProfileResponse>) {
        // Muestra las respuestas en el RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewAnswers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterAnswer(answers, profiles)
    }

    /**
     * Sets up the button to return to the previous screen.
     */
    private fun setupBackButton() {
        // Configura el botón para regresar a la pantalla anterior
        val btnBackForum: ImageButton = findViewById(R.id.btnBackForum)
        btnBackForum.setOnClickListener {
            finish()
        }
    }
}