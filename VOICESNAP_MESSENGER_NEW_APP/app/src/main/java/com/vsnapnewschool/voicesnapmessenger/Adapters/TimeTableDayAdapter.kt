package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetDatesHomeWorkListResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetDaysTimeTable
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import java.util.*


class TimeTableDayAdapter (private val dateslist: ArrayList<GetDaysTimeTable.DateData>,
                           private val context: Context?,
                           val btnlistener: BtnClickListener) : RecyclerView.Adapter<TimeTableDayAdapter.MyViewHolder>() {
    companion object {
        var mClickListener: BtnClickListener? = null
    }
    private var row_index: Int = 0
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblDay: TextView
        internal var lblDate: TextView
        internal var lblyear: TextView
        internal var LayoutOverall: ConstraintLayout
        init {
            lblDay = view.findViewById<View>(R.id.lblDay) as TextView
            lblDate = view.findViewById<View>(R.id.lblDate) as TextView
            lblyear = view.findViewById<View>(R.id.lblYear) as TextView
            LayoutOverall = view.findViewById<View>(R.id.LayoutOverall) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.timetable_calendar, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mClickListener = btnlistener
        val text_info = dateslist[position]
        holder.lblDate.visibility=View.VISIBLE
        holder.lblyear.visibility=View.VISIBLE
        holder.run {
            lblDay.text = text_info.day_name
//            lblDate.text = text_info.date
//            lblyear.text = text_info.year
            holder.LayoutOverall.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    row_index = position
                    notifyDataSetChanged()
                    if (mClickListener != null)
                        mClickListener?.onBtnClick(position,text_info)
                }
            })



            if (row_index == position) {
                UtilConstants.DateIdTimeTable=text_info.day_id
                mClickListener?.onBtnClick(position,text_info)
                lblDay.text = text_info.day_name
                holder.LayoutOverall.setBackgroundResource(R.drawable.parent_blue_bg)
                holder.lblDay.setTextColor(Color.parseColor("#FFFEFE"))



            } else {
                lblDay.text = text_info.day_name
                holder.LayoutOverall.setBackgroundResource(R.drawable.blue_box)
                holder.lblDay.setTextColor(Color.parseColor("#1fb8fd"))

            }



        }
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int,datelist: GetDaysTimeTable.DateData)
    }



    override fun getItemCount(): Int {
        return dateslist.size

    }
}
