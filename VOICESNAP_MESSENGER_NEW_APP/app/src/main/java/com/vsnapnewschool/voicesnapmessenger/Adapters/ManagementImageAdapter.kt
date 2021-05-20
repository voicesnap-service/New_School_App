package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementImageListener
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetImageFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromMangementImageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import java.util.*

class ManagementImageAdapter  (private val listname: ArrayList<MessageFromMangementImageResponse.ImageData>, private val context: Context?, val imagelistListener: managementImageListener) : RecyclerView.Adapter<ManagementImageAdapter.MyViewHolder>() {
    companion object {
        var imageclickListener: managementImageListener? = null
    }

    var imageCountlist = ArrayList<GetImageFilesResponse.GetImageData.FileArray>()
    var imageCount:Int=0

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgGrid: ImageView
        internal var lblCreatedBy: TextView
        internal var lblTitle: TextView
        internal var lblCreatedOn: TextView
        internal var lblNew: TextView
        internal var lblImageCount: TextView

        internal var progressBar: ProgressBar

        init {
            imgGrid = view.findViewById<View>(R.id.imgGrid) as ImageView
            lblCreatedBy = view.findViewById<View>(R.id.lblCreatedBy) as TextView
            lblTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            lblNew = view.findViewById<View>(R.id.lblNew) as TextView
            lblCreatedOn = view.findViewById<View>(R.id.lblCreatedOn) as TextView
            lblImageCount = view.findViewById<View>(R.id.lblImageCount) as TextView
            progressBar = view.findViewById<View>(R.id.progressImage) as ProgressBar

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_image_adapter_design, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        holder.run {
            imageclickListener = imagelistListener
            imageclickListener?.onManagementImageClick(holder,text_info)
            imageCountlist= text_info.file_array as ArrayList<GetImageFilesResponse.GetImageData.FileArray>
            Log.d("imageCountlist",imageCountlist.size.toString())
            if(imageCountlist!=null){
                imageCount=imageCountlist.size

            }else{
                UtilConstants.normalToast(context as Activity,"No Records Found")
            }
            holder.lblTitle.text = text_info.description
            lblCreatedOn.text = text_info.createdon
            lblCreatedBy.text = text_info.created_by

            if(imageCountlist.size>1){
                imageCount = imageCount - 1
                holder.lblImageCount.setText("+"+imageCount)
            }
            else{
            lblImageCount.text= R.string.txt_clcik_image.toString()
        }
//
//            imageCountlist.forEach({
//                var filepath=it.file_path
//                if (context != null) {
//                    Glide.with(context)
//                        .load(filepath)
//                        .into(holder.imgGrid)
//                }
//            })

            if (imageCountlist != null) {
                Glide.with(context!!)
                    .load(imageCountlist.get(0).file_path)
                    .into(holder.imgGrid)
            }
            if(text_info.app_viewed == 1){
                holder.lblNew.visibility= View.GONE
            }
            else{
                holder.lblNew.visibility= View.VISIBLE
            }
        }

    }
    override fun getItemCount(): Int {
        return listname.size
    }
}




