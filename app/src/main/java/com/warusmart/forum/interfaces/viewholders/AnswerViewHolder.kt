package com.warusmart.forum.interfaces.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.warusmart.R
import com.warusmart.forum.domain.model.Answer
import com.warusmart.iam.domain.model.ProfileResponse

class AnswerViewHolder(view: View, val profileList: List<ProfileResponse>): RecyclerView.ViewHolder(view) {

    val answerCont = view.findViewById<TextView>(R.id.txtContentAnswer)
    val user = view.findViewById<TextView>(R.id.txtUserAnswer)

    fun render(answer: Answer){
        val profile = profileList.find { it.id == answer.authorId }

        answerCont.text = answer.answerText
        user.text = profile?.fullName ?: "Unknown"
    }
}