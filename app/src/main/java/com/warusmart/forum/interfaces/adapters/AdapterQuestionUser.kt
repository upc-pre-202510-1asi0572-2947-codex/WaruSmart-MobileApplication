package com.warusmart.forum.interfaces.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.forum.interfaces.viewholders.QuestionUserViewHolder
import com.warusmart.R
import com.warusmart.forum.domain.model.Category
import com.warusmart.forum.domain.model.Question

/**
 * Adapter to display the user's questions in a RecyclerView.
 * Associates each question with its corresponding category.
 */
class AdapterQuestionUser(
    val questionsList: List<Question>,
    val categoryiesList: List<Category>): RecyclerView.Adapter<QuestionUserViewHolder>() {

    /**
     * Inflates the ViewHolder for each user question.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionUserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuestionUserViewHolder(layoutInflater.inflate(R.layout.user_question_item, parent, false), categoryiesList)
    }

    /**
     * Returns the number of user questions.
     */
    override fun getItemCount(): Int = questionsList.size

    /**
     * Binds the question data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: QuestionUserViewHolder, position: Int) {
        val item = questionsList[position]
        holder.render(item)
    }

}