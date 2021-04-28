package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

import java.io.Serializable

data class StandardSectionsResponse(
    val `data`: List<StandardData>,
    val message: String,
    val status: Int
)

data class StandardData(
    val section_list: List<Section>,
    val standard_id: String,
    val standard_name: String
)

data class Section(
    val section_id: String,
    val section_name: String,

    var selectStatus: Boolean

)