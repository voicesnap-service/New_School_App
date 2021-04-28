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
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class TimeDayAdapter(
    private val imagelist: ArrayList<DayCLass>,
    private val context: Context?,
    private val type: String,
    private var row_index: Int = -1,
    val btnlistener: BtnClickListener) : RecyclerView.Adapter<TimeDayAdapter.MyViewHolder>() {
    companion object {
        var mClickListener: BtnClickListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblDay: TextView
        internal var LayoutOverall: ConstraintLayout
        init {
            lblDay = view.findViewById<View>(R.id.lblDay) as TextView
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
        val text_info = imagelist[position]
        holder.run {
            lblDay.text = text_info.day
            holder.LayoutOverall.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    row_index = position
                    notifyDataSetChanged()
                    if (mClickListener != null)
                        mClickListener?.onBtnClick(position)
                }
            })

            if (row_index == position) {
                if(type.equals("1")) {
                    lblDay.text = text_info.day
                    holder.LayoutOverall.setBackgroundResource(R.drawable.rectangle_orange)
                    holder.lblDay.setTextColor(Color.parseColor("#FFFEFE"))
                    Toast.makeText(context,text_info.day , Toast.LENGTH_SHORT).show();

                }else{
                    lblDay.text = text_info.day
                    holder.LayoutOverall.setBackgroundResource(R.drawable.parent_blue_bg)
                    holder.lblDay.setTextColor(Color.parseColor("#FFFEFE"))
                    Toast.makeText(context,text_info.day , Toast.LENGTH_SHORT).show();

                }
            } else {

                if(type.equals("1")) {
                    holder.LayoutOverall.setBackgroundResource(R.drawable.orange_box)
                    holder.lblDay.setTextColor(Color.parseColor("#fb6b22"))
                }else{
                    holder.LayoutOverall.setBackgroundResource(R.drawable.blue_box)
                    holder.lblDay.setTextColor(Color.parseColor("#1fb8fd"))
                }

            }



        }
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int)
    }



    override fun getItemCount(): Int {
        return imagelist.size

    }
}
