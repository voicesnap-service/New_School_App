package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class GenerateNewLoginTokenResponse(
    val `data`: List<TokenData>,
    val message: String,
    val status: Int
)

data class TokenData(
    val token: String
)