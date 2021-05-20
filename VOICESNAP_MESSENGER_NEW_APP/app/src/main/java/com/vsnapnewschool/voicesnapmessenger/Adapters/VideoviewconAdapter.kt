package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.vsnapnewschool.voicesnapmessenger.Activities.ParentVideoView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.videoViewListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R


class VideoviewconAdapter(private val imagelist: ArrayList<Text_Class>,
                          private val context: Context?,
                          val videoViewListener: videoViewListener
) : RecyclerView.Adapter<VideoviewconAdapter.MyViewHolder>() {

    companion object {
        var videoClickListener: videoViewListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var Videoview: VideoView
        internal var imgthumb: ShapeableImageView
        init {
            Videoview = view.findViewById<View>(R.id.videoview) as VideoView
            imgthumb = view.findViewById<View>(R.id.imgthumb) as ShapeableImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.videoview_adpt, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        videoClickListener = videoViewListener
//        videoClickListener?.videoViewClick(holder,text_info)
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
