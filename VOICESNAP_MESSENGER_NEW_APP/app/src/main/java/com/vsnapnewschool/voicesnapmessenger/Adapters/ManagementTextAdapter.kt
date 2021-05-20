package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementTextListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementTextResponse
import java.util.ArrayList

class ManagementTextAdapter (private var listname: ArrayList<MessageFromManagementTextResponse.TextData>, private val context: Context, val textClickListener: managementTextListener) : RecyclerView.Adapter<ManagementTextAdapter.MyViewHolder>() {
    companion object {
        var clickListener: managementTextListener? = null
    }

    fun update(updateTextList: ArrayList<MessageFromManagementTextResponse.TextData>) {
        listname = updateTextList
        notifyDataSetChanged()
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblmessage: TextView
        internal var lblCreatedBySort: TextView
        internal var lblCreatedOn: TextView
        internal var lblNew: TextView
        internal var LayoutContent: ConstraintLayout
        internal var lblContainer: ConstraintLayout

        init {
            lblmessage = view.findViewById<View>(R.id.lblmessage) as TextView
            lblCreatedBySort = view.findViewById<View>(R.id.lblCreatedBySort) as TextView
            lblCreatedOn = view.findViewById<View>(R.id.lblCreatedOn) as TextView
            lblNew = view.findViewById<View>(R.id.lblNew) as TextView
            LayoutContent = view.findViewById<View>(R.id.LayoutContent) as ConstraintLayout
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
        clickListener?.onManagementTextClick(holder,text_info)
        holder.lblmessage.text = text_info.content
        holder.lblCreatedBySort.text=text_info.created_by_short
        holder.lblCreatedOn.text=text_info.created_on

        holder.LayoutContent.setBackgroundResource(R.drawable.rectangle_orange)
        holder.lblNew.setBackgroundResource(R.drawable.whitebg_curve)
        holder.lblNew.setTextColor(Color.parseColor("#fb6b22"))
        holder.lblCreatedBySort.setBackgroundResource(R.drawable.rect_orange_white)

        if(text_info.app_viewed == 1){
            holder.lblNew.visibility= View.GONE
        }
        else{
            holder.lblNew.visibility= View.VISIBLE
        }
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}


