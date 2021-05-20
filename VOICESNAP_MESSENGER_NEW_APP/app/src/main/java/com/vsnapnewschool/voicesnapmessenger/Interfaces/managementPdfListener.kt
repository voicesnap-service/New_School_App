package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementPdfAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementPdfResponse

interface managementPdfListener {
    fun onpdfclickListener(holder: ManagementPdfAdapter.MyViewHolder, item: MessageFromManagementPdfResponse.PdfData)

}