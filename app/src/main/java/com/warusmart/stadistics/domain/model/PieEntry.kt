package com.warusmart.stadistics.domain.model

/**
 * Data class representing a pie chart entry for statistics.
 * Used for charting control or crop distributions.
 */
data class PieEntry (
    val label: String,
    val value: Int
)