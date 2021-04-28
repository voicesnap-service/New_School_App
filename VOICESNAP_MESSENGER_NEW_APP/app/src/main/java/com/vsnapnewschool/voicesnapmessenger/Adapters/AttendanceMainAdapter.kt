package com.vsnapnewschool.voicesnapmessenger.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.markattendanceListener
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*
class AttendanceMainAdapter(private val imagelist: ArrayList<DayCLass>,
                            private val context: Context?,
                            val attendaceListener: markattendanceListener
                            ) : RecyclerView.Adapter<AttendanceMainAdapter.MyViewHolder>() {
    companion object {
        var attendencelistener: markattendanceListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var btnattendance: Button
        internal var LayoutAttendance: ConstraintLayout
        init {
            btnattendance = view.findViewById<View>(R.id.btnattendance) as Button
            LayoutAttendance = view.findViewById<View>(R.id.LayoutAttendance) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.attendance_adpt, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.run {
            holder.btnattendance.text = text_info.day
            var attendnaceType =text_info.day
            attendencelistener = attendaceListener
            attendencelistener?.onmarkAttendanceClick(holder,text_info)
            if(attendnaceType.equals("Attendance")) {
                holder.LayoutAttendance.setBackgroundResource(R.drawable.rectangle_orange)
            } else{
                holder.LayoutAttendance.setBackgroundResource(R.drawable.rectangle_grey)
            }
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
