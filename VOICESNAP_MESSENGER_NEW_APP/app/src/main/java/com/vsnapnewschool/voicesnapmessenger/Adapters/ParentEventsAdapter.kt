package com.vsnapnewschool.voicesnapmessenger.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.eventsparentListener
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.EventsData
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*

class ParentEventsAdapter(
    private val listname: ArrayList<EventsData>,
    private val context: Context?,
    private val type: Boolean,
    val eventListener: eventsparentListener
) : RecyclerView.Adapter<ParentEventsAdapter.MyViewHolder>() {

    companion object {
        var eventadapterClick: eventsparentListener? = null
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var content: TextView
        internal var time: TextView
        internal var lblTitle: TextView
        internal var lblSentBy: TextView
        internal var circular: ConstraintLayout
        internal var CircularLayout: ConstraintLayout

        init {
            content = view.findViewById<View>(R.id.lblcontent) as TextView
            time = view.findViewById<View>(R.id.lblsent) as TextView
            lblTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            lblSentBy = view.findViewById<View>(R.id.lblSentBy) as TextView
            circular = view.findViewById<View>(R.id.circular) as ConstraintLayout
            CircularLayout = view.findViewById<View>(R.id.CircularLayout) as ConstraintLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_circular_card, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        eventadapterClick = eventListener
        eventadapterClick?.oneventClick(holder, text_info)
        holder.lblTitle.text = text_info.description
        holder.time.text = text_info.event_time
        holder.lblSentBy.text = text_info.created_by

        if (type) {
            holder.CircularLayout.setBackgroundResource(R.drawable.blue_shadow)

        }



    }

    override fun getItemCount(): Int {
        return listname.size

    }
}



