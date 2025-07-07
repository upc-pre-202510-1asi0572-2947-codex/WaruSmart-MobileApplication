package com.warusmart.sowings.domain.model

/**
 * Data class representing a crop entity.
 * Used to store crop information such as id, name, image, and description.
 */
class Crop(
    var id: Int,
    var name: String,
    var imageUrl: String,
    var description: String,
/*    var diseases: List<Int>,
    var pests: List<Int>,
    var cares: List<Int>*/
) {

}