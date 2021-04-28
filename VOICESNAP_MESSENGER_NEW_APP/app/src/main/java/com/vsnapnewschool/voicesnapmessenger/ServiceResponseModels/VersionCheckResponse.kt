package com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels

data class VersionCheckResponse(
    val `data`: List<VersionData>,
    val message: String,
    val status: Int
)

data  class VersionData(
    val AlertContent: String,
    val AlertTitle: String,
    val ForceUpdate: Int,
    val ReportingLink: String,
    val UpdateAvailable: Int,
    val VersionAlertContent: String,
    val VersionAlertTitle: String,
    val isAlertAvailable: Int
)