package com.vsnapnewschool.voicesnapmessenger.Models

data class apiVersioncheck(

    val attendancedaycount: String,
    val eventphotoscount: String,
    val faq: String,
    val help: String,
    val imagecount: String,
    val isforceupdaterequired: Int,
    val isversionupdateavailable: Int,
    val pdfcount: String,
    val playstorelink: String,
    val playstoremarketid: String,
    val privarypolicy: String,
    val resultmessage: String,
    val resultvalue: Int,
    val termsandcondition: String,
    val versionalertcontent: String,
    val versionalerttitle: String,
    val videojson: String,
    val videosizealert: String,
    val videosizelimit: String
)