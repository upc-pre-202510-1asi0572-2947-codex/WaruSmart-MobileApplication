package com.example.warusmart.forum.application.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.warusmart.forum.application.viewholders.AnswerViewHolder
import com.example.warusmart.R
import com.example.warusmart.forum.domain.model.Answer
import com.example.warusmart.iam.domain.model.ProfileResponse

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