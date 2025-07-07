package com.warusmart.crops.domain.model.beans

import java.util.Date

/**
 * Class representing a sowing entity with its properties.
 */
class Sowing(
    var id: Int,
    var startDate: Date,
    var endDate: Date,
    var areaLand: Int,
    var status: Boolean,
    var phenologicalPhase: Int,
    var cropId: Int,
    var phenologicalPhaseName: String
)