package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class TimeClassAdapter(
    private val imagelist: ArrayList<DayCLass>,
    private val context: Context?,
    private val type: Boolean) : RecyclerView.Adapter<TimeClassAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lnrSubject: LinearLayout
        init {
            lnrSubject = view.findViewById<View>(R.id.lnrSubject) as LinearLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.timetable_adpt, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (type) {
            holder.lnrSubject.setBackgroundResource(R.drawable.ttparent_shadow)
        }else {
            holder.lnrSubject.setBackgroundResource(R.drawable.timetable_shadow)
        }

    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
