package com.example.waruSmart_appmovil_android.forum.application.viewholders

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.waruSmart_appmovil_android.forum.interfaces.AnswersActivity
import com.example.waruSmart_appmovil_android.forum.interfaces.ForumManagementActivity
import com.example.waruSmart_appmovil_android.R
import com.example.waruSmart_appmovil_android.SessionManager
import com.example.waruSmart_appmovil_android.forum.domain.model.Category
import com.example.waruSmart_appmovil_android.forum.domain.model.Question

class QuestionUserViewHolder(view: View, private val categories: List<Category>): RecyclerView.ViewHolder(view) {

    val questionCont = view.findViewById<TextView>(R.id.txtQuestionUser)
    val category = view.findViewById<TextView>(R.id.txtCategory)
    val date = view.findViewById<TextView>(R.id.txtQuestionDate)
    val btnSeeQuestion = view.findViewById<ImageButton>(R.id.btnSeeQuestion)
    val btnEditQuestion = view.findViewById<ImageButton>(R.id.btnEditQuestion)
    val btnDeleteQuestion = view.findViewById<ImageButton>(R.id.btnDeleteQuestion)


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