package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.CircularHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse

interface circularHistorylistener {

    fun oncircularclickListener(holder: CircularHistoryAdapter.MyViewHolder,item: GetPdfFilesResponse.PdfData)

}