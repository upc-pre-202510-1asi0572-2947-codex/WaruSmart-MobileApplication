package com.example.warusmart.forum.application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warusmart.forum.application.viewholders.QuestionUserViewHolder
import com.example.warusmart.R
import com.example.warusmart.forum.domain.model.Category
import com.example.warusmart.forum.domain.model.Question

class AdapterQuestionUser(
    val questionsList: List<Question>,
    val categoryiesList: List<Category>): RecyclerView.Adapter<QuestionUserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionUserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuestionUserViewHolder(layoutInflater.inflate(R.layout.user_question_item, parent, false), categoryiesList)
    }

    override fun getItemCount(): Int = questionsList.size

    override fun onBindViewHolder(holder: QuestionUserViewHolder, position: Int) {
        val item = questionsList[position]
        holder.render(item)
    }

}