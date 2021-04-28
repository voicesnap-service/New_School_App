package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkAllStandardListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.AllStandardData

class AllStandardAdapter(private val mContext: Context, private val standardlistener: checkAllStandardListener?,
                         private var standardList: List<AllStandardData>,private var isSelectAll: Boolean) :
    RecyclerView.Adapter<AllStandardAdapter.MyViewHolder>() {
    private var position1 = 0
    private var isSelect = true


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_section_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        position1 = holder.adapterPosition
        holder.lblStandardSectionName.setText(standardList[position].standard_name)

        if(isSelect) {
            if (isSelectAll) {
                holder.chbox.setChecked(true)
            } else {
                holder.chbox.setChecked(false)

            }
        }

        holder.chbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isSelect =false
                standardlistener!!.addStandard(standardList[position])
            } else {
                isSelect =false
                standardlistener!!.removeStandard(standardList[position])
            }
        })
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
        return standardList.size
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}