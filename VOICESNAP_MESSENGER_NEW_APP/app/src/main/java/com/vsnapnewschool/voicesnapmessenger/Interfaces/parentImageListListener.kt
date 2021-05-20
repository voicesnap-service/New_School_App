package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageListAdapter
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetImageFilesResponse


interface parentImageListListener {

    fun onImageListClick(holder: ParentImageListAdapter.MyViewHolder, item: GetImageFilesResponse.GetImageData)

}