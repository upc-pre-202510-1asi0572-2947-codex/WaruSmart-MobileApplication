package com.warusmart.crops.domain.model.beans

import java.util.Date

data class Cares(
    val id: Int,
    val suggestion: String,
    val date: Date,
    val sowing: Sowing
)