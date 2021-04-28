package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R

import java.util.*


class VideoviewallAdapter(private val imagelist: ArrayList<Text_Class>, private val context: Context?) : RecyclerView.Adapter<VideoviewallAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var video: ConstraintLayout
        init {
            video = view.findViewById<View>(R.id.video) as ConstraintLayout

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.video_adpt, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
