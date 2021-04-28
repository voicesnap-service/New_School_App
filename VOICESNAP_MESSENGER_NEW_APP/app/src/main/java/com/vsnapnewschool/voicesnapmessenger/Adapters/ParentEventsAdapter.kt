package com.vsnapnewschool.voicesnapmessenger.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Interfaces.eventsparentListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*
class ParentEventsAdapter(private val listname: ArrayList<EventsImageClass>, private val context: Context?, private  val type: Boolean,val eventListener: eventsparentListener) : RecyclerView.Adapter<ParentEventsAdapter.MyViewHolder>() {

    companion object {
            var eventadapterClick: eventsparentListener? = null
        }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblDate: TextView
        internal var lblMonth: TextView
        internal var lblEventTitle: TextView
        internal var lblEventPlace: TextView
        internal var imgEventsImage: ImageView
        internal var lnrDate: LinearLayout
        init {
            lblDate = view.findViewById<View>(R.id.lblDate) as TextView
            lblMonth = view.findViewById<View>(R.id.lblMonth) as TextView
            lblEventTitle = view.findViewById<View>(R.id.lblEventTitle) as TextView
            lblEventPlace = view.findViewById<View>(R.id.lblEventPlace) as TextView
            imgEventsImage = view.findViewById<View>(R.id.imgEventsImage) as ImageView
            lnrDate = view.findViewById<View>(R.id.lnrDate) as LinearLayout

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_events_images, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        eventadapterClick = eventListener
        eventadapterClick?.oneventClick(holder,text_info)
        holder.lblDate.text = text_info.Day
        holder.lblMonth.text = text_info.Month
        holder.lblEventTitle.text = text_info.Content
        holder.lblEventPlace.text = text_info.description

        if(type){
            holder.lnrDate.setBackgroundResource(R.drawable.parent_blue_bg)

        }
        context?.let {
            Glide.with(it)
                .load(text_info.image)
                .into(holder.imgEventsImage)
        }

    }
    override fun getItemCount(): Int {
        return listname.size

    }
}



