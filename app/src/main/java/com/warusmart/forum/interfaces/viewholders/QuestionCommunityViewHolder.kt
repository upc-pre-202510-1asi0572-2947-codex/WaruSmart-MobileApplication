package com.warusmart.forum.interfaces.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.forum.domain.model.Question

class QuestionCommunityViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val questionCont = view.findViewById<TextView>(R.id.txtQuestionComm)

    fun render(question: Question){
        questionCont.text = question.questionText
    }
}