package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkAllStaffsListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkConferenceListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.AllStaffsData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ConferenceStaffResponse

class ConferenceAdapter(private val mContext: Context, private val staffslistener: checkConferenceListener?, private var staffsList: List<ConferenceStaffResponse.ConferenceData>, private var isSelectAll: Boolean) : RecyclerView.Adapter<ConferenceAdapter.MyViewHolder>() {
    private var position1 = 0
    private var isSelect = true


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_section_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        position1 = holder.adapterPosition
        holder.lblStandardSectionName.setText(staffsList[position].staff_name)


        if(isSelect) {
            if (isSelectAll) {
                holder.chbox.setChecked(true)
            } else {
                holder.chbox.setChecked(false)

            }
        }

        holder.chbox.setOnCheckedChangeListener(null)
        holder.chbox.setOnCheckedChangeListener{ buttonView, isChecked ->
            if (isChecked) {
                isSelect =false
                staffslistener!!.addStaff(staffsList[position])
            } else {
                isSelect =false
                staffslistener!!.removeStaff(staffsList[position])
            }
        }

    }
    inner class MyViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        var lblStandardSectionName: TextView
        var chbox: CheckBox
        init {
            lblStandardSectionName = view.findViewById<View>(R.id.lblStandardSectionName) as TextView
            chbox = view.findViewById<View>(R.id.chbox) as CheckBox
        }
    }
    override fun getItemCount(): Int {
        return staffsList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}
