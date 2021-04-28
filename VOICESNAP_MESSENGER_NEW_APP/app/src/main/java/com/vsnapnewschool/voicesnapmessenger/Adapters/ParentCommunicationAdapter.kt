package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentCommunicationListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class ParentCommunicationAdapter(private var imagelist: ArrayList<Text_Class>,
                                 private val context: Context?,val communicationListener:parentCommunicationListener) : RecyclerView.Adapter<ParentCommunicationAdapter.MyViewHolder>() {
    fun update(modelList:ArrayList<Text_Class>){
        imagelist = modelList
        notifyDataSetChanged()
    }
    companion object {
        var listener: parentCommunicationListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var communication: ConstraintLayout
        internal var lblName: TextView
        internal var lblTitle: TextView
        init {
            lblName = view.findViewById<View>(R.id.lblName) as TextView
            lblTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            communication = view.findViewById<View>(R.id.communication) as ConstraintLayout

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.parent_communication_profile, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.run {
            holder.lblName.text=text_info.recipients
            holder.lblTitle.text=text_info.content
            listener = communicationListener
            listener?.oncommunicationClick(holder,text_info)

        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
