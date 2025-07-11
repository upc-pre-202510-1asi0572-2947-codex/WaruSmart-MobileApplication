package com.warusmart.crops.domain.model.beans

/**
 * Class representing a crop entity with its properties and related lists.
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