package com.example.warusmart.stadistics.domain.model

class Crop(
    var id: Int,
    var name: String,
    var imageUrl: String,
    var diseases: List<Int>,
    var pests: List<Int>,
    var cares: List<Int>
) {

}