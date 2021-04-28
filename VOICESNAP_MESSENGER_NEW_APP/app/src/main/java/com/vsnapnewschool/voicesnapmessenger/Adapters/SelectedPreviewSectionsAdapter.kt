package com.vsca.schoolnewvsnap.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkFinalSectionListener
import com.vsnapnewschool.voicesnapmessenger.Models.SelecetedPreviewSection

import com.vsnapnewschool.voicesnapmessenger.R

class SelectedPreviewSectionsAdapter(private val mContext: Context, private val sectionlistener: checkFinalSectionListener?,
                                     private var sectionList: List<SelecetedPreviewSection>) :
    RecyclerView.Adapter<SelectedPreviewSectionsAdapter.MyViewHolder>() {
    private var position1 = 0
    fun updateList(temp: List<SelecetedPreviewSection>) {
        sectionList = temp
        notifyDataSetChanged()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_section_items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        position1 = holder.adapterPosition
        holder.lblStandardSectionName.setText(sectionList[position].sectionName)
        holder.chbox.isChecked=true
        sectionlistener!!.addFinalSection(sectionList[position])
        holder.chbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sectionlistener.addFinalSection(sectionList[position])
            } else {
                sectionlistener.removeFinalSection(sectionList[position])
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
        return sectionList.size
    }

}