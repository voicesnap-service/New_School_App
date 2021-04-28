package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GetAllSubjects(
    val `data`: List<SubjectsData>,
    val message: String,
    val status: Int
)

data class SubjectsData(
    val subject_id: String,
    val subject_name: String
)