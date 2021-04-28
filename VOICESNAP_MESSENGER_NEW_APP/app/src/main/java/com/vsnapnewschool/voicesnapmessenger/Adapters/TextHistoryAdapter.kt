package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.textHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class TextHistoryAdapter(private val imagelist: ArrayList<Text_Class>,  val context: Context?,val textHistoryListener: textHistoryListener) : RecyclerView.Adapter<TextHistoryAdapter.MyViewHolder>() {
    companion object {
        var textlistener: textHistoryListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var content: TextView
        internal var time: TextView
        internal var count: TextView
        internal var status: TextView
        internal var lnrText: RelativeLayout
        init {
            content = view.findViewById<View>(R.id.lblContent) as TextView
            time = view.findViewById<View>(R.id.lbltime) as TextView
            count = view.findViewById<View>(R.id.lblcount) as TextView
            status = view.findViewById<View>(R.id.lblstatus) as TextView
            lnrText = view.findViewById<View>(R.id.lnrText) as RelativeLayout

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.text_adpt, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val text_info = imagelist[position]


        textlistener = textHistoryListener
        textlistener?.textHistoryClick(holder,text_info)

        holder.content.text = text_info.content
        holder.time.text = text_info.time
        holder.count.text = text_info.recipients
        holder.status.text = text_info.status
        holder.run {
            content.text = text_info.content
            time.text = text_info.time
            count.text = text_info.recipients
            status.text = text_info.status
        }
    }
    override fun getItemCount(): Int {
        return imagelist.size

    }
}
