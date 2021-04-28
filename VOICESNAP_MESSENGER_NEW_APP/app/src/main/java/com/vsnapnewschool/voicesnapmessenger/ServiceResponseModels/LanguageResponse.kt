package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class LanguageResponse(
    val `data`: List<LanguageData>,
    val message: String,
    val status: Int
)

data class LanguageData(
    val Id: String,
    val Language: String,
    val ScriptCode: String
)