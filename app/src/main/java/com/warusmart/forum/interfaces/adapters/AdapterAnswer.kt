package com.warusmart.forum.interfaces.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.forum.interfaces.viewholders.AnswerViewHolder
import com.warusmart.R
import com.warusmart.forum.domain.model.Answer
import com.warusmart.iam.domain.model.ProfileResponse

/**
 * Adapter to display answers in a RecyclerView.
 * Associates each answer with the profile of its author.
 */
class AdapterAnswer(val answersList: List<Answer>, val profileList: List<ProfileResponse>): RecyclerView.Adapter<AnswerViewHolder>() {
    /**
     * Inflates the ViewHolder for each answer.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AnswerViewHolder(layoutInflater.inflate(R.layout.answer_item, parent, false), profileList)
    }

    /**
     * Returns the number of answers in the list.
     */
    override fun getItemCount(): Int = answersList.size

    /**
     * Binds the answer data to the ViewHolder.
     */
    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val item = answersList[position]
        holder.render(item)
    }
}