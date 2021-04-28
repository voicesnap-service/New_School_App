package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.noticeboardListener
import com.vsnapnewschool.voicesnapmessenger.Models.Image_Class
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*

class ParentNoticeBoardAdapter(
    private val listname: ArrayList<Image_Class>, private val context: Context?,private val type: String,val noticeboardListener: noticeboardListener) : RecyclerView.Adapter<ParentNoticeBoardAdapter.MyViewHolder>() {

    companion object {
        var noticeclickListener: noticeboardListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var title: TextView
        internal var timing: TextView
        internal var content: TextView
        internal var rytHistory: RelativeLayout
        init {
            title = view.findViewById<View>(R.id.lblHeading) as TextView
            timing = view.findViewById<View>(R.id.lblTiming) as TextView
            content = view.findViewById<View>(R.id.lblHistory) as TextView
            rytHistory = view.findViewById<View>(R.id.rytHistory) as RelativeLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_noticeboard, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        noticeclickListener = noticeboardListener
        noticeclickListener?.noticeboardClick(holder,text_info)
        holder.title.text = text_info.title
        holder.timing.text = text_info.timing
        holder.content.text = text_info.content

    }
    override fun getItemCount(): Int {
        return listname.size

    }
}
