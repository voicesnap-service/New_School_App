package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.examResultListener
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class ParentExamResultAdapter(private val imagelist: ArrayList<DayCLass>, private val context: Context?, val examResultListener:examResultListener) : RecyclerView.Adapter<ParentExamResultAdapter.MyViewHolder>() {
    companion object {
        var listener: examResultListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lbldetails: TextView
        init {
            lbldetails = view.findViewById<View>(R.id.lbldetails) as TextView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_subject_progress, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.run {
            listener = examResultListener
            listener?.onexamResultClick(holder,text_info)
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size
    }
}
