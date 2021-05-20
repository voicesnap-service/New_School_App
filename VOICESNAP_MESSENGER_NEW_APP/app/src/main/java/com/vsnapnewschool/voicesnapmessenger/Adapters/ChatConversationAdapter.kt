package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.PopupListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ChatData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatScreenDetails
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatScreenResponse
import java.util.*

class ChatConversationAdapter(private val listname: ArrayList<ChatData>, private val context: Context, private  val type :Boolean, popupListener: PopupListener) : RecyclerView.Adapter<ChatConversationAdapter.MyViewHolder>() {
    var popupListener: PopupListener? = popupListener

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgheadset: ImageView
        internal var txtMyMessage: TextView
        internal var txtMyMessageTime: TextView
        internal var lblReciver: TextView
        internal var txtMyMessageTime1: TextView
        internal var lblstudentname: TextView
        internal var lblstaffname: TextView
        internal var menu: ImageView
        internal var conAns: ConstraintLayout
        init {
            imgheadset = view.findViewById<View>(R.id.imgheadset) as ImageView
            menu = view.findViewById<View>(R.id.menu) as ImageView
            txtMyMessage = view.findViewById<View>(R.id.txtMyMessage) as TextView
            txtMyMessageTime = view.findViewById<View>(R.id.txtMyMessageTime) as TextView
            lblReciver = view.findViewById<View>(R.id.lblReciver) as TextView
            lblstudentname = view.findViewById<View>(R.id.lblstudentname) as TextView
            lblstaffname = view.findViewById<View>(R.id.lblstaffname) as TextView
            txtMyMessageTime1 = view.findViewById<View>(R.id.txtMyMessageTime1) as TextView
            conAns = view.findViewById<View>(R.id.conAns) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_sender_design, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        holder.imgheadset.setImageResource(R.drawable.teacher_home_orange)
        holder.lblstudentname.setText(text_info.student_name)
        holder.lblstaffname.setText("Me")
        holder.lblReciver.setText(text_info.question)
        holder.txtMyMessageTime1.setText(text_info.created_on)
        holder.menu.visibility=View.VISIBLE

        if(text_info.answered_on.isEmpty()){
            holder.conAns.visibility=View.GONE
            holder.imgheadset.visibility=View.GONE
        }
        else {
            holder.conAns.visibility==View.VISIBLE
            holder.imgheadset.visibility==View.VISIBLE
            holder.txtMyMessage.setText(text_info.answer)
            holder.txtMyMessageTime.setText(text_info.answered_on)
        }

        holder.menu.setOnClickListener({
            val popup = PopupMenu(context,holder.menu)
            popup.menuInflater.inflate(R.menu.answer_menu, popup.menu)
            if (text_info.change_answer.equals("1"))
                popup.menu.findItem(R.id.answer).isVisible = false
            else
                popup.menu.findItem(R.id.change_answer).isVisible = false
            popup.show()
            popup.setOnMenuItemClickListener { item ->
                if (item.itemId == R.id.answer) {
                    popupListener!!.click("Answer", text_info)
                    true
                }  else {
                    popupListener!!.click("ChangeAnswer", text_info)
                    true
                }
            }
        })

    }
    override fun getItemCount(): Int {
        return listname.size

    }
}



