package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.circularHistorylistener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class CircularHistoryAdapter(
    private var imagelist: ArrayList<Text_Class>,
    private val context: Context?,
    private val type: String,
    val ciruclarlistener:circularHistorylistener) : RecyclerView.Adapter<CircularHistoryAdapter.MyViewHolder>() {
    fun update(modelList:ArrayList<Text_Class>){
        imagelist = modelList
        notifyDataSetChanged()
    }
    companion object {
        var circularhistorylistener: circularHistorylistener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var content: TextView
        internal var time: TextView
        internal var lblTitle: TextView
        internal var circular: ConstraintLayout
        internal var CircularLayout: ConstraintLayout
        init {
            content = view.findViewById<View>(R.id.lblcontent) as TextView
            time = view.findViewById<View>(R.id.lblsent) as TextView
            lblTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            circular = view.findViewById<View>(R.id.circular) as ConstraintLayout
            CircularLayout = view.findViewById<View>(R.id.CircularLayout) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_circular_card, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = imagelist[position]
        holder.run {
            circularhistorylistener = ciruclarlistener
            circularhistorylistener?.oncircularclickListener(holder,text_info)
            content.text = text_info.content
            time.text = text_info.time
            lblTitle.text = text_info.recipients
            if (type.equals("0")) {
                CircularLayout.setBackgroundResource(R.drawable.blue_shadow)

            }
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
