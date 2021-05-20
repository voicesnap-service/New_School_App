package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementImageAdapter
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromMangementImageResponse

interface managementImageListener {
    fun onManagementImageClick(holder: ManagementImageAdapter.MyViewHolder, item: MessageFromMangementImageResponse.ImageData)

}