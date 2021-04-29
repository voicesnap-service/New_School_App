package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.TextMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextData
import java.util.ArrayList

class ParentTextMessageAdapter(private val listname: ArrayList<GetTextData>, private val context: Context,val textClickListener: TextMessagesClickListener) : RecyclerView.Adapter<ParentTextMessageAdapter.MyViewHolder>() {
    companion object {
        var clickListener: TextMessagesClickListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblmessage: TextView
        internal var lblCreatedBySort: TextView
        internal var lblCreatedOn: TextView
        internal var lblNew: TextView
        internal var lblContainer: ConstraintLayout

        init {
            lblmessage = view.findViewById<View>(R.id.lblmessage) as TextView
            lblCreatedBySort = view.findViewById<View>(R.id.lblCreatedBySort) as TextView
            lblCreatedOn = view.findViewById<View>(R.id.lblCreatedOn) as TextView
            lblNew = view.findViewById<View>(R.id.lblNew) as TextView
            lblContainer = view.findViewById<View>(R.id.lblContainer) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_parent_text_message_view, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val text_info = listname[position]
        clickListener = textClickListener
        clickListener?.onTextClick(holder,text_info)
        holder.lblmessage.text = text_info.content
        holder.lblCreatedBySort.text=text_info.created_by_short
        holder.lblCreatedOn.text=text_info.created_on

        if(text_info.app_viewed == 1){
            holder.lblNew.visibility=View.GONE
        }
        else{
            holder.lblNew.visibility=View.VISIBLE
        }
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}


