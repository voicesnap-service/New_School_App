package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementVideoListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.videoViewListener
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.ParentVideoDetail
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementTextResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementVideoResponse
import java.util.ArrayList

class ManagementVideoAdapter (private val videolist: ArrayList<MessageFromManagementVideoResponse.VideoData>, private val context: Context?, val videoViewListener: managementVideoListener) : RecyclerView.Adapter<ManagementVideoAdapter.MyViewHolder>() {

    companion object {
        var videoClickListener: managementVideoListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var video: ConstraintLayout
        internal var lblsubject: TextView
        internal var lbltitle: TextView
        internal var lblsent: TextView
        init {
            video = view.findViewById<View>(R.id.video) as ConstraintLayout
            lblsubject = view.findViewById<View>(R.id.lblsubject) as TextView
            lbltitle = view.findViewById<View>(R.id.lbltitle) as TextView
            lblsent = view.findViewById<View>(R.id.lblsent) as TextView

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_adpt, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val videolist= videolist[position]
        holder.lblsubject.setText(videolist.title)
        holder.lbltitle.setText(videolist.description)
        holder.lblsent.setText(videolist.created_by+" - "+videolist.created_on)
        videoClickListener = videoViewListener
        videoClickListener?.onManagementVideoClick(holder,videolist)

    }
    override fun getItemCount(): Int {
        return videolist.size

    }
}
