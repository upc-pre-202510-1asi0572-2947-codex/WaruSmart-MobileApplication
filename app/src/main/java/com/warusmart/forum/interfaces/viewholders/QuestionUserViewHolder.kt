package com.warusmart.forum.interfaces.viewholders

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.forum.interfaces.activities.AnswersActivity
import com.warusmart.forum.interfaces.activities.ForumManagementActivity
import com.warusmart.R
import com.warusmart.iam.domain.model.SessionManager
import com.warusmart.forum.domain.model.Category
import com.warusmart.forum.domain.model.Question

/**
 * ViewHolder to display a user's question in the forum, with actions to view, edit, and delete.
 */
class QuestionUserViewHolder(view: View, private val categories: List<Category>): RecyclerView.ViewHolder(view) {

    val questionCont = view.findViewById<TextView>(R.id.txtQuestionUser)
    val category = view.findViewById<TextView>(R.id.txtCategory)
    val date = view.findViewById<TextView>(R.id.txtQuestionDate)
    val btnSeeQuestion = view.findViewById<ImageButton>(R.id.btnSeeQuestion)
    val btnEditQuestion = view.findViewById<ImageButton>(R.id.btnEditQuestion)
    val btnDeleteQuestion = view.findViewById<ImageButton>(R.id.btnDeleteQuestion)


    /**
     * Renders the question and sets up action listeners for the UI.
     */
    fun render(question: Question){
        questionCont.text = question.questionText
        date.text = question.date.toString().take(10)
        val profileId = SessionManager.profileId

        val categoryName = categories.find { it.categoryId == question.categoryId }?.name ?: "Unknown"
        category.text = categoryName

        btnSeeQuestion.setOnClickListener{
            val context = itemView.context
            val intent = Intent(context, AnswersActivity::class.java).apply {
                putExtra("question", question)
                putExtra("isFromCommunity", false)
            }
            context.startActivity(intent)
        }

        btnEditQuestion.setOnClickListener {
            val context = itemView.context as AppCompatActivity
            (context as ForumManagementActivity).showEditQuestionDialog(question, categoryName, profileId)
        }

        btnDeleteQuestion.setOnClickListener {
            val context = itemView.context as AppCompatActivity
            (context as ForumManagementActivity).showDeleteQuestionDialog(question, profileId)
        }
    }

}