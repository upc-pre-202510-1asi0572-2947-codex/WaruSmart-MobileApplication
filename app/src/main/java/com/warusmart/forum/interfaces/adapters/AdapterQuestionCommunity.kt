package com.warusmart.forum.interfaces.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.forum.interfaces.activities.AnswersActivity
import com.warusmart.forum.interfaces.viewholders.QuestionCommunityViewHolder
import com.warusmart.R
import com.warusmart.forum.domain.model.Question

/**
 * Adapter to display community questions in a RecyclerView.
 * Allows navigation to the answers screen when a question is selected.
 */
class AdapterQuestionCommunity(val questionsList: List<Question>):RecyclerView.Adapter<QuestionCommunityViewHolder>() {
    /**
     * Inflates the ViewHolder for each community question.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionCommunityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuestionCommunityViewHolder(layoutInflater.inflate(R.layout.community_question_item, parent, false))
    }

    /**
     * Returns the number of questions in the list.
     */
    override fun getItemCount(): Int = questionsList.size

    /**
     * Binds the question data to the ViewHolder and sets up click to view answers.
     */
    override fun onBindViewHolder(holder: QuestionCommunityViewHolder, position: Int) {
        val item = questionsList[position]
        holder.render(item)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AnswersActivity::class.java)
            intent.putExtra("question", item)
            intent.putExtra("isFromCommunity", true)
            context.startActivity(intent)
        }
    }

}