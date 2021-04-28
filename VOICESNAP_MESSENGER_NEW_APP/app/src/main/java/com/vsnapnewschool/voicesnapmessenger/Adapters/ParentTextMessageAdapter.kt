package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.Voice_Class
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.ArrayList

class ParentTextMessageAdapter(private val listname: ArrayList<Voice_Class>, private val context: Context) : RecyclerView.Adapter<ParentTextMessageAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblmessage: TextView
        internal var lblcountmsg: TextView
        internal var lblunread: TextView

        init {
            lblmessage = view.findViewById<View>(R.id.lblmessage) as TextView
            lblcountmsg = view.findViewById<View>(R.id.lblcountmsg) as TextView
            lblunread = view.findViewById<View>(R.id.lblunread) as TextView


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_parent_text_message_view, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val text_info = listname[position]
        holder.lblunread.visibility=View.GONE
        holder.lblmessage.text = text_info.content
//        holder.lblcountmsg.text = text_info.title


    }
    override fun getItemCount(): Int {
        return listname.size
    }
}


