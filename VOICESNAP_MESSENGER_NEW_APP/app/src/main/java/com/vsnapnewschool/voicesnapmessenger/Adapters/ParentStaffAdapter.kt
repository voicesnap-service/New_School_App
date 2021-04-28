package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class ParentStaffAdapter(private val imagelist: ArrayList<class_chat>, private val context: Context?) : RecyclerView.Adapter<ParentStaffAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblname: TextView
        internal var lbldesignation: TextView
        internal var imgstaff: ImageView
        init {

            lblname = view.findViewById<View>(R.id.lblname) as TextView
            lbldesignation = view.findViewById<View>(R.id.lbldesignation) as TextView
            imgstaff = view.findViewById<View>(R.id.imgstaff) as ImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.staff_design, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.lbldesignation.text = text_info.title
        holder.lblname.text = text_info.timing
        if (context != null) {
            Glide.with(context)
                .load(text_info.image)
                .circleCrop()
                .into(holder.imgstaff)
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
