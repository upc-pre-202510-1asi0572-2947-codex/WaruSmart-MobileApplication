package com.warusmart.forum.interfaces.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.forum.domain.model.Question

/**
 * ViewHolder to display a community question in the forum.
 */
class QuestionCommunityViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val questionCont = view.findViewById<TextView>(R.id.txtQuestionComm)

    /**
     * Renders the question content in the UI.
     */
    fun render(question: Question){
        questionCont.text = question.questionText
    }
}