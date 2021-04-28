package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*
class ChatConversationAdapter(private val listname: ArrayList<class_chat>, private val context: Context,private  val type :Boolean) : RecyclerView.Adapter<ChatConversationAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgheadset: ImageView
        init {
            imgheadset = view.findViewById<View>(R.id.imgheadset) as ImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_sender_design, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        if(type){
            holder.imgheadset.setImageResource(R.drawable.teacher_home_orange)
        }else{
            holder.imgheadset.setImageResource(R.drawable.prnt_group)
        }
    }
    override fun getItemCount(): Int {
        return listname.size

    }
}



