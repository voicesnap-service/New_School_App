package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.homeworkListener
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetHomeWorkListResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetTimeTableList
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class TimeTableListAdapter(
    private var timetableList: ArrayList<GetTimeTableList.TimeTableData>,
    private val context: Context?,
    private val type: String
) : RecyclerView.Adapter<TimeTableListAdapter.MyViewHolder>() {
    companion object {
        var homeworkClick: homeworkListener? = null
    }
    fun update(modelList:ArrayList<GetTimeTableList.TimeTableData>){
        timetableList = modelList
        notifyDataSetChanged()
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lnrSubject: LinearLayout
        internal var lblHour: TextView
        internal var lblstarttime: TextView
        internal var lblendtime: TextView
        internal var lblSubject: TextView


        init {
            lnrSubject = view.findViewById<View>(R.id.lnrSubject) as LinearLayout
            lblHour = view.findViewById<View>(R.id.lblHourName) as TextView
            lblstarttime = view.findViewById<View>(R.id.lblstarttime) as TextView
            lblendtime = view.findViewById<View>(R.id.lblendtime) as TextView
            lblSubject = view.findViewById<View>(R.id.lblSubject) as TextView

        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.timetable_adpt, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = timetableList[position]
        holder.run {


            lblHour.text = text_info.hour_name+" "+"-"+" "+text_info.duration+"mins"
            lblSubject.text = text_info.subject
            lblstarttime.text = text_info.start_time
            lblendtime.text = text_info.end_time
//            sentat.visibility = View.INVISIBLE
            if (type.equals("0")) {
                holder.lnrSubject.setBackgroundResource(R.drawable.ttparent_shadow)

            }
//            else {
//                date.visibility = View.INVISIBLE
//
//            }

        }
    }
    override fun getItemCount(): Int {
        return timetableList.size

    }
}
