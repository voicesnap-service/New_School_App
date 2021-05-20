package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.circularHistorylistener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.classStrengthListener
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetClassWiseStrength
import java.util.ArrayList

class ClassWiseStrengthAdapter (
    private var list: ArrayList<GetClassWiseStrength.ClassStrengthData>,
    private val context: Context?,
    private val type: String,
    val listener: classStrengthListener
) : RecyclerView.Adapter<ClassWiseStrengthAdapter.MyViewHolder>() {

    companion object {
        var strengthlistener: classStrengthListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblStudentCount: TextView
        internal var lblGirlsCount: TextView
        internal var lblBoysCount: TextView
        internal var lblUnspecified: TextView
        internal var lblClassName: TextView
        internal var LayoutClick: ConstraintLayout

        init {
            lblStudentCount = view.findViewById<View>(R.id.lblStudentCount) as TextView
            lblGirlsCount = view.findViewById<View>(R.id.lblGirlsCount) as TextView
            lblBoysCount = view.findViewById<View>(R.id.lblBoysCount) as TextView
            lblUnspecified = view.findViewById<View>(R.id.lblUnspecifiedCount) as TextView
            lblClassName = view.findViewById<View>(R.id.lblStandardName) as TextView
            LayoutClick = view.findViewById<View>(R.id.LayoutClick) as ConstraintLayout
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
            strengthlistener?.onClassStrength(holder,text_info)
            lblUnspecified.text = text_info.student_unspecified_count
            lblBoysCount.text = text_info.student_boys_count
            lblGirlsCount.text = text_info.student_girls_count
            lblStudentCount.text = text_info.total_student_count
            lblClassName.text = text_info.class_name

        }
    }
    override fun getItemCount(): Int {
        return list.size

    }
}
