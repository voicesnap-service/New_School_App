package com.vsnapnewschool.voicesnapmessenger.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Interfaces.imageListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class ParentImageAdapter(private val listname: ArrayList<String>, private val context: Context?,val imageListener: imageListener) : RecyclerView.Adapter<ParentImageAdapter.MyViewHolder>() {
    companion object {
        var imageclickListener: imageListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgGrid: ImageView
        init {
          imgGrid = view.findViewById<View>(R.id.imgGrid) as ImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_image_screen, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        imageclickListener = imageListener
        imageclickListener?.onimageClick(holder,text_info)
        if (context != null) {
            Glide.with(context)
                .load(text_info)
                .into(holder.imgGrid)
        }
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}




