package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkSchoolListListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.schoolClickListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffDetailData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import java.util.ArrayList

class SchoolListAdapter(private val schoolsList: ArrayList<StaffDetailData>, private val context: Context?,private val listener:checkSchoolListListener?, val schoolsListener: schoolClickListener) : RecyclerView.Adapter<SchoolListAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblSchoolName: TextView
        internal var constSchools: ConstraintLayout
        internal var chooseCheck: CheckBox
        internal var imgArrow: ImageView
        init {
            lblSchoolName = view.findViewById<View>(R.id.lblSchoolName) as TextView
            constSchools = view.findViewById<View>(R.id.constSchools) as ConstraintLayout
            chooseCheck = view.findViewById<View>(R.id.chooseCheck) as CheckBox
            imgArrow = view.findViewById<View>(R.id.imgArrow) as ImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.school_list_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(UtilConstants.MENU_TYPE == UtilConstants.MENU_EMERGENCY){
            holder.chooseCheck.visibility=View.VISIBLE
            holder.imgArrow.visibility=View.GONE
        }
        else{
            holder.chooseCheck.visibility=View.GONE
            holder.imgArrow.visibility=View.VISIBLE
        }
        val school_info = schoolsList[position]
        schoolsListener.onclickListener(holder,school_info)
        holder.lblSchoolName.setText(school_info.school_name)

        holder.chooseCheck.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                listener!!.add(schoolsList[position])
            } else {
                listener!!.remove(schoolsList[position])
            }
        })


    }
    override fun getItemCount(): Int {
        return schoolsList.size
    }
}