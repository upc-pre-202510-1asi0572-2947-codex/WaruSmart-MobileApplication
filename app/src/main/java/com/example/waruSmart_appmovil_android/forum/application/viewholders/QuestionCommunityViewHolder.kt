package com.example.waruSmart_appmovil_android.forum.application.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.waruSmart_appmovil_android.R
import com.example.waruSmart_appmovil_android.forum.domain.model.Question

class QuestionCommunityViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val questionCont = view.findViewById<TextView>(R.id.txtQuestionComm)

    fun render(question: Question){
        questionCont.text = question.questionText
    }
}