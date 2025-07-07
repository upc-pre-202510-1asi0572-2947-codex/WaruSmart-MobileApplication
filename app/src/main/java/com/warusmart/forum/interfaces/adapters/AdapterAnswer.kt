package com.warusmart.forum.interfaces.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.forum.interfaces.viewholders.AnswerViewHolder
import com.warusmart.R
import com.warusmart.forum.domain.model.Answer
import com.warusmart.iam.domain.model.ProfileResponse

class AdapterAnswer(val answersList: List<Answer>, val profileList: List<ProfileResponse>): RecyclerView.Adapter<AnswerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AnswerViewHolder(layoutInflater.inflate(R.layout.answer_item, parent, false), profileList)
    }

    override fun getItemCount(): Int = answersList.size

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        val item = answersList[position]
        holder.render(item)
    }
}