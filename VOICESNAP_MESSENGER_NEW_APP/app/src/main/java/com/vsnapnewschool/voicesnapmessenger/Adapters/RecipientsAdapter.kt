package com.vsnapnewschool.voicesnapmessenger.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R


class RecipientsAdapter(private val recipientlist: ArrayList<String>, private val context: Context?) : RecyclerView.Adapter<RecipientsAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblRecipients: TextView
        init {
            lblRecipients = view.findViewById<View>(R.id.lblRecipients) as TextView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_receipients, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = recipientlist[position]
        holder.run {
            lblRecipients.text = text_info
        }
    }
    override fun getItemCount(): Int {
        return recipientlist.size

    }
}
