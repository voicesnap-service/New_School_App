package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.classStrengthListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.sectionStrengthListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetClassWiseStrength
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetSectionWiseStrength
import java.util.ArrayList

class SectionWiseStrengthAdapter( private var list: ArrayList<GetSectionWiseStrength.SectionStrengthData>,
private val context: Context?,
private val type: String,
val listener: sectionStrengthListener
) : RecyclerView.Adapter<SectionWiseStrengthAdapter.MyViewHolder>() {

    companion object {
        var strengthlistener: sectionStrengthListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblStudentCount: TextView
        internal var lblGirlsCount: TextView
        internal var lblBoysCount: TextView
        internal var lblUnspecified: TextView
        internal var lblClassName: TextView
        internal var LayoutClick: ConstraintLayout
        internal var imgNext: ImageView

        init {
            lblStudentCount = view.findViewById<View>(R.id.lblStudentCount) as TextView
            lblGirlsCount = view.findViewById<View>(R.id.lblGirlsCount) as TextView
            lblBoysCount = view.findViewById<View>(R.id.lblBoysCount) as TextView
            lblUnspecified = view.findViewById<View>(R.id.lblUnspecifiedCount) as TextView
            lblClassName = view.findViewById<View>(R.id.lblStandardName) as TextView
            LayoutClick = view.findViewById<View>(R.id.LayoutClick) as ConstraintLayout
            imgNext = view.findViewById<View>(R.id.imgNext) as ImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.class_wise_strength, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = list[position]
        holder.run {
            strengthlistener = listener
            strengthlistener?.onSectionStrength(holder,text_info)
            lblUnspecified.text = text_info.student_unspecified_count
            lblBoysCount.text = text_info.student_boys_count
            lblGirlsCount.text = text_info.student_girls_count
            lblStudentCount.text = text_info.total_student_count
            lblClassName.text = text_info.section_name

            imgNext.visibility=View.INVISIBLE

        }
    }
    override fun getItemCount(): Int {
        return list.size

    }
}
