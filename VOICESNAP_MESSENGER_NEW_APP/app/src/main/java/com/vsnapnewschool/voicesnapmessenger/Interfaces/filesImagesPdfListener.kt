package com.vsnapnewschool.voicesnapmessenger.Interfaces

import com.vsnapnewschool.voicesnapmessenger.Adapters.FilesAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentPdfAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse

interface filesImagesPdfListener {

    fun onFilesClick(holder: FilesAdapter.MyViewHolder, image: FilesImagePDF)
    fun onPdfFilesClick(holder: ParentPdfAdapter.MyViewHolder, pdf:FilesImagePDF)

}