package com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse

import java.io.Serializable

data class GetDatesHomeWorkListResponse(
    val `data`: List<GetDatelist>,
    val message: String,
    val status: Int
):Serializable {

    data class GetDatelist(
        val year: String,
        val month_name: String,
        val date: String,
        val homework_date: String
    )
}
//    {
//        "status": 1,
//        "message": "List Received Successfully",
//        "data": [
//        {
//            "Year": "2021",
//            "monthName": "May",
//            "Date": "12"
//        },
//        {
//            "Year": "2021",
//            "monthName": "May",
//            "Date": "11"
//        },
//        {
//            "Year": "2021",
//            "monthName": "May",
//            "Date": "10"
//        },
//        {
//            "Year": "2021",
//            "monthName": "May",
//            "Date": "09"
//        },
//        {
//            "Year": "2021",
//            "monthName": "May",
//            "Date": "08"
//        },
//        {
//            "Year": "2021",
//            "monthName": "May",
//            "Date": "07"
//        },
//        {
//            "Year": "2021",
//            "monthName": "May",
//            "Date": "06"
//        }
//        ]
//    }