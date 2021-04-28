package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Models.ImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*



class ParentLanguageAdapter(private val listname: ArrayList<ImageClass>, private val context: Context?) : RecyclerView.Adapter<ParentLanguageAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblTitle: TextView
        internal var imgFlag: ImageView
        init {
            lblTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            imgFlag = view.findViewById<View>(R.id.imgFlag) as ImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.language_list_design, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        holder.lblTitle.text = text_info.Day
        this.context?.let {
            Glide.with(it)
                .load(text_info.image)
                .into(holder.imgFlag)
        }
    }
    override fun getItemCount(): Int {
        return listname.size

    }
}



