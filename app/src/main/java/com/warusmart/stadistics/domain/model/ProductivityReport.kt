package com.warusmart.stadistics.domain.model

/**
 * Data class representing a productivity report for statistics.
 * Contains report attributes such as id, crop name, production, and area.
 */
class ProductivityReport(
    var id: Int,
    var name: String,
    var production: Float,
    var area: Float
) {
}