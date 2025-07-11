package com.warusmart.forum.interfaces.activities

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.shared.interfaces.BaseActivity
import com.warusmart.R
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.forum.interfaces.adapters.AdapterQuestionCommunity
import com.warusmart.forum.interfaces.adapters.AdapterQuestionUser
import com.warusmart.forum.domain.model.Category
import com.warusmart.forum.domain.model.DateFormat
import com.warusmart.forum.application.services.CategoriesService
import com.warusmart.forum.application.services.QuestionsService
import com.warusmart.forum.domain.model.Question
import com.warusmart.forum.domain.model.QuestionPost
import com.warusmart.forum.domain.model.QuestionPut
import com.warusmart.forum.application.services.ProfileServiceForum
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

/**
 * Activity for managing forum questions, including adding, editing, and deleting questions.
 * Allows users to view community questions or their own questions based on the selected option.
 */
class ForumManagementActivity : BaseActivity() {

    private lateinit var questionsService: QuestionsService
    private lateinit var categoriesService: CategoriesService
    private lateinit var profileService: ProfileServiceForum


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Initializes the view and required services for forum management.
         */
        layoutInflater.inflate(R.layout.activity_forum_management, findViewById(R.id.container))
        enableEdgeToEdge()

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.selectedItemId = R.id.navigation_forum

        questionsService = QuestionsService()
        categoriesService = CategoriesService()
        profileService = ProfileServiceForum()

        val profileId = SessionManager.profileId


        setupAutoCompleteTextView()
        setupAddQuestionButton(profileId)
        fetchAndDisplayQuestionsCommunity()
        fetchAndDisplayQuestionsUser()
    }

    override fun onResume() {
        super.onResume()
        /**
         * Sets up the autocomplete and add question button on resume.
         */
        setupAutoCompleteTextView()
        setupAddQuestionButton(SessionManager.profileId)

        val autoComplete: AutoCompleteTextView = findViewById(R.id.spinnerForum)
        autoComplete.setText("Community", false)
    }

    /**
     * Sets up the AutoCompleteTextView to switch between community and user questions.
     */
    private fun setupAutoCompleteTextView() {
        // Configura el AutoCompleteTextView para alternar entre preguntas de la comunidad y del usuario
        val item = listOf("Community", "My Questions")
        val autoComplete: AutoCompleteTextView = findViewById(R.id.spinnerForum)
        val adapter = ArrayAdapter(this, R.layout.section_forum_list, item)
        autoComplete.setAdapter(adapter)
        autoComplete.setText(item[0], false)
        autoComplete.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            if (selectedItem == "Community") {
                clearQuestions()
                fetchAndDisplayQuestionsCommunity()
            }else if (selectedItem == "My Questions") {
                clearQuestions()
                fetchAndDisplayQuestionsUser()
            }
            Toast.makeText(this, " $selectedItem", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Sets up the button to add a new question.
     */
    private fun setupAddQuestionButton(profileId:Int) {
        // Configura el botón para agregar una nueva pregunta
        val addQuestionButton: Button = findViewById(R.id.btnAddQuestion)
        addQuestionButton.setOnClickListener {
            showAddQuestionDialog(profileId)
        }
    }

    /**
     * Shows a dialog to add a new question.
     */
    private fun showAddQuestionDialog(profileId:Int) {
        // Muestra un diálogo para agregar una nueva pregunta
        val dialogView = layoutInflater.inflate(R.layout.add_edit_question_dialog, null)
        val editTextQuestion: EditText = dialogView.findViewById(R.id.editTextQuestion)
        val spinnerCategory: AutoCompleteTextView = dialogView.findViewById(R.id.spinnerCategory)
        val btnAddQuestion: Button = dialogView.findViewById(R.id.btnAddQuestion)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        val questionDialogTitle: TextView = dialogView.findViewById(R.id.questionDialogTitle)
        // Establecer el texto del TextView
        questionDialogTitle.text = "Add New Question"
        btnAddQuestion.text = "Add"
        var selectedCategoryId: Int = 0
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = categoriesService.getAllCategories().map { Pair(it.categoryId, it.name) }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(this@ForumManagementActivity, android.R.layout.simple_dropdown_item_1line, categories.map { it.second })
                    spinnerCategory.setAdapter(adapter)
                    spinnerCategory.setOnItemClickListener { parent, view, position, id ->
                        selectedCategoryId = categories[position].first
                        // Almacena el ID de la categoría seleccionada para su uso posterior
                    }
                }
            } catch (e: Exception) {
                Log.e("ForumManagementActivity", "Error: ${e.message}")
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
        btnAddQuestion.setOnClickListener {
            val questionText = editTextQuestion.text.toString()
            val categoryId = selectedCategoryId
            if (selectedCategoryId == 0 || questionText.isBlank()) {
                Toast.makeText(this, "Please select a category and enter the question content", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val date = DateFormat.format(Date())
            val question = QuestionPost(SessionManager.profileId, categoryId, questionText, date)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    questionsService.addQuestion(question)
                    withContext(Dispatchers.Main) {
                        fetchAndDisplayQuestionsCommunity()
                        Toast.makeText(this@ForumManagementActivity, "Question added successfully", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ForumManagementActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /**
     * Shows a dialog to edit an existing question.
     */
    fun showEditQuestionDialog(question: Question, categoryName: String, profileId:Int){
        // Muestra un diálogo para editar una pregunta existente
        val dialogView = layoutInflater.inflate(R.layout.add_edit_question_dialog, null)
        val editTextQuestion: EditText = dialogView.findViewById(R.id.editTextQuestion)
        val spinnerCategory: AutoCompleteTextView = dialogView.findViewById(R.id.spinnerCategory)
        val btnAddQuestion: Button = dialogView.findViewById(R.id.btnAddQuestion)
        val btnCancel: Button = dialogView.findViewById(R.id.btnCancel)
        val questionDialogTitle: TextView = dialogView.findViewById(R.id.questionDialogTitle)
        questionDialogTitle.text = "Edit Question"
        btnAddQuestion.text = "Update"
        editTextQuestion.setText(question.questionText)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = categoriesService.getAllCategories().map { Pair(it.categoryId, it.name) }
                withContext(Dispatchers.Main) {
                    val adapter = ArrayAdapter(this@ForumManagementActivity, android.R.layout.simple_dropdown_item_1line, categories.map { it.second })
                    spinnerCategory.setAdapter(adapter)
                    spinnerCategory.setText(categoryName, false)
                    spinnerCategory.setOnItemClickListener { parent, view, position, id ->
                        question.categoryId = categories[position].first
                    }
                }
            } catch (e: Exception) {
                Log.e("ForumManagementActivity", "Error: ${e.message}")
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
        btnAddQuestion.setOnClickListener {
            question.questionText = editTextQuestion.text.toString()
            val updatedQuestion = QuestionPut(question.categoryId, question.questionText)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    questionsService.updateQuestion(question.questionId, updatedQuestion)
                    withContext(Dispatchers.Main) {
                        fetchAndDisplayQuestionsUser()
                        Toast.makeText(this@ForumManagementActivity, "Question updated successfully", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ForumManagementActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /**
     * Shows a dialog to confirm the deletion of a question.
     */
    fun showDeleteQuestionDialog(question: Question, profileId:Int) {
        // Muestra un diálogo para confirmar la eliminación de una pregunta
        val dialogView = layoutInflater.inflate(R.layout.delete_question_dialog, null)
        val deleteTitle: TextView = dialogView.findViewById(R.id.titleDeleteDialog)
        val deleteText: TextView = dialogView.findViewById(R.id.textDeleteDialog)
        val btnYes: Button = dialogView.findViewById(R.id.btnYes)
        val btnNo: Button = dialogView.findViewById(R.id.btnNo)
        deleteTitle.text = "Delete Question"
        deleteText.text = "Are you sure you want to delete this question?"
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()
        btnYes.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    questionsService.deleteQuestion(question.questionId)
                    withContext(Dispatchers.Main) {
                        fetchAndDisplayQuestionsUser()
                        Toast.makeText(this@ForumManagementActivity, "Question deleted successfully", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ForumManagementActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btnNo.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    /**
     * Fetches and displays community questions.
     */
    private fun fetchAndDisplayQuestionsCommunity(){
        // Obtiene y muestra las preguntas de la comunidad
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val questions = questionsService.getAllQuestions()
                withContext(Dispatchers.Main){
                    displayQuestionsCommunity(questions)
                }
            } catch (e: Exception){
                Log.e("CommunityQuestionActivity", "Error: ${e.message}")
            }
        }
    }

    /**
     * Displays community questions in the RecyclerView.
     */
    private fun displayQuestionsCommunity(questions: List<Question>){
        // Muestra las preguntas de la comunidad en el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewQuestions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterQuestionCommunity(questions)
    }
    /**
     * Clears the list of questions in the RecyclerView.
     */
    private fun clearQuestions() {
        // Limpia la lista de preguntas en el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewQuestions)
        recyclerView.adapter = AdapterQuestionCommunity(emptyList())
    }

    /**
     * Fetches and displays the current user's questions.
     */
    private fun fetchAndDisplayQuestionsUser(){
        // Obtiene y muestra las preguntas del usuario actual
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val categories = categoriesService.getAllCategories()
                val questions = questionsService.getQuestionsByAuthorId(SessionManager.profileId)
                withContext(Dispatchers.Main){
                    displayQuestionsUser(questions, categories)
                }
            } catch (e: Exception){
                Log.e("ForumActivity", "Error: ${e.message}")
            }
        }
    }

    /**
     * Displays the user's questions in the RecyclerView.
     */
    private fun displayQuestionsUser(questions: List<Question>, categories: List<Category>){
        // Muestra las preguntas del usuario en el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewQuestions)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AdapterQuestionUser(questions, categories)

    }
}