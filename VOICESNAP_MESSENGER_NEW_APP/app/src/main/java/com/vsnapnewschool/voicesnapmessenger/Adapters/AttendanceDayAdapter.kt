//package com.vsnapnewschool.voicesnapmessenger.Adapters
//
//import android.content.Context
//import android.graphics.Color
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import android.widget.Toast
//import androidx.constraintlayout.widget.ConstraintLayout
//import androidx.recyclerview.widget.RecyclerView
//import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
//import com.vsnapnewschool.voicesnapmessenger.R
//import java.util.*
//
//
//class AttendanceDayAdapter(private val imagelist: ArrayList<DayCLass>,
//                           private val context: Context?,
//                           private var row_index: Int = -1,
//                           val btnlistener: BtnClickListener) : RecyclerView.Adapter<AttendanceDayAdapter.MyViewHolder>() {
//    companion object {
//        var mClickListener: BtnClickListener? = null
//    }
//    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        internal var lblDay: TextView
//        internal var lblDate: TextView
//        internal var LayoutOverall: ConstraintLayout
//        init {
//            lblDay = view.findViewById<View>(R.id.lblDay) as TextView
//            lblDate = view.findViewById<View>(R.id.lblDate) as TextView
//            LayoutOverall = view.findViewById<View>(R.id.LayoutOverall) as ConstraintLayout
//        }
//    }
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.attendance_calendar, parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        mClickListener = btnlistener
//
//        val text_info = imagelist[position]
//        holder.run {
//            lblDay.text = text_info.day
//            holder.LayoutOverall.setOnClickListener(object : View.OnClickListener {
//                override fun onClick(v: View?) {
//                    row_index = position
//                    notifyDataSetChanged()
//                    if (mClickListener != null)
//                        mClickListener?.onBtnClick(position)
//                }
//            })
//            if (row_index == position) {
//                holder.LayoutOverall.setBackgroundResource(R.drawable.rectangle_orange)
//                lblDay.text = text_info.day
//                holder.lblDate.setTextColor(Color.parseColor("#FFFEFE"))
//                holder.lblDay.setTextColor(Color.parseColor("#FFFEFE"))
//                Toast.makeText(context,text_info.day , Toast.LENGTH_SHORT).show();
//            } else {
//                holder.LayoutOverall.setBackgroundResource(R.drawable.grey_box)
//                holder.lblDate.setTextColor(Color.parseColor("#FF000000"))
//                holder.lblDay.setTextColor(Color.parseColor("#FF000000"))
//
//            }
//        }
//    }
//    interface BtnClickListener {
//        fun onBtnClick(position: Int)
//    }
//    override fun getItemCount(): Int {
//        return imagelist.size
//
//    }
//}
