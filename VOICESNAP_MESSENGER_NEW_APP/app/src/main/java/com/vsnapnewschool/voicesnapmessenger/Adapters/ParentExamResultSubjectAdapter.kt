package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R

import java.util.*


class ParentExamResultSubjectAdapter(private val imagelist: ArrayList<DayCLass>, private val context: Context?) : RecyclerView.Adapter<ParentExamResultSubjectAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        internal var lbldetails: TextView
//        init {
//            lbldetails = view.findViewById<View>(R.id.lbldetails) as TextView
//        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_subject_marks, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.run {
//            lbldetails.setOnClickListener({
//                val intent = Intent(context, ExamResViewParent::class.java)
//                context?.startActivity(intent)
//            })
//        }

    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
