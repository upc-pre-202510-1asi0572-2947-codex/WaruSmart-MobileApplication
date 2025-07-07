package com.warusmart.stadistics.domain.model

/**
 * Data class representing a crop for statistics.
 * Contains crop attributes such as id, name, and image URL.
 */
class Crop(
    var id: Int,
    var name: String,
    var imageUrl: String,
    var diseases: List<Int>,
    var pests: List<Int>,
    var cares: List<Int>
) {

}