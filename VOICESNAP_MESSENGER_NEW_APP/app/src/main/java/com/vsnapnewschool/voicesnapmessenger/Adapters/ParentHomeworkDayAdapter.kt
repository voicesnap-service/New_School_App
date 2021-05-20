package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetDatesHomeWorkListResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import java.util.*


class ParentHomeworkDayAdapter(
    private val dateslist: ArrayList<GetDatesHomeWorkListResponse.GetDatelist>,
    private val context: Context?,
    val btnlistener: BtnClickListener) : RecyclerView.Adapter<ParentHomeworkDayAdapter.MyViewHolder>() {
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
            lblDay.text = text_info.month_name
            lblDate.text = text_info.date
            lblyear.text = text_info.year
            holder.LayoutOverall.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    row_index = position
                    notifyDataSetChanged()
                    if (mClickListener != null)
                        mClickListener?.onBtnClick(position,text_info)
                }
            })



            if (row_index == position) {
                UtilConstants.Datehomework=text_info.homework_date
                mClickListener?.onBtnClick(position,text_info)
                lblDay.text = text_info.month_name
                lblDate.text = text_info.date
                lblyear.text = text_info.year
                holder.LayoutOverall.setBackgroundResource(R.drawable.parent_blue_bg)
                holder.lblDay.setTextColor(Color.parseColor("#FFFEFE"))
                holder.lblDate.setTextColor(Color.parseColor("#FFFEFE"))
                holder.lblyear.setTextColor(Color.parseColor("#FFFEFE"))


            } else {


                lblDay.text = text_info.month_name
                lblDate.text = text_info.date
                lblyear.text = text_info.year
                holder.LayoutOverall.setBackgroundResource(R.drawable.blue_box)
                holder.lblDay.setTextColor(Color.parseColor("#1fb8fd"))
                holder.lblDate.setTextColor(Color.parseColor("#1fb8fd"))
                holder.lblyear.setTextColor(Color.parseColor("#1fb8fd"))


            }



        }
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int,datelist: GetDatesHomeWorkListResponse.GetDatelist)
    }



    override fun getItemCount(): Int {
        return dateslist.size

    }
}
