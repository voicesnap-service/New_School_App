package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class CountryListResponse(
    val `data`: List<CountryData>,
    val message: String,
    val status: Int
)

data class CountryData(
    val base_url: String,
    val country_logo: String,
    val country_code: Int,
    val country_name: String,
    val id: Int,
    val mobile_number_length: Int
)