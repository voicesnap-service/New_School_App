package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkSectionsListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.Section
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.section_student_list_item.view.*

class SectionListAdapter(
    private val sectionList: ArrayList<Section>,
    private val sectionlistener: checkSectionsListener?,
    private val SelectedHashmapSatandardSectionList: HashMap<String, MutableList<Section>>,
    private val StandardID: String
) :
    RecyclerView.Adapter<SectionListAdapter.ViewHolder>() {

    override fun getItemCount() = sectionList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.section_student_list_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val Section = sectionList[position]
        holder.lblSectinName.setText(Section.section_name)
        holder.checkbox.setOnCheckedChangeListener(null)

        if (UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE_HOMEWORK || UtilConstants.MENU_TYPE == UtilConstants.MENU_TEXT_HOMEWORK || UtilConstants.MENU_TYPE == UtilConstants.MENU_ASSIGNMENT) {

        }
        else{
        if (!SelectedHashmapSatandardSectionList.isEmpty()) {
            if (SelectedHashmapSatandardSectionList.containsKey(StandardID)) {
                if (SelectedHashmapSatandardSectionList.get(StandardID)!!.contains(Section)) {
                    holder.checkbox.isChecked = true
                }
            }

        }
    }
        holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                sectionlistener!!.add(Section)
            } else {
                sectionlistener!!.remove(Section)
            }
        })

    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox: CheckBox = itemView.chooseCheck
        val lblSectinName: TextView = itemView.lblSectinName
    }
}