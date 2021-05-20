package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentChatData
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*

class ParentChatConversationAdapter(
    private val listname: ArrayList<StudentChatData>,
    private val context: Context,
    val staffname: String
) : RecyclerView.Adapter<ParentChatConversationAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var imgheadset: ImageView
        internal var txtMyMessage: TextView
        internal var txtMyMessageTime: TextView
        internal var lblParentReciver: TextView
        internal var txtParentTime1: TextView
        internal var lblstaffname: TextView
        internal var lblstaffAnsname: TextView
        internal var menu: ImageView
        internal var conAns: ConstraintLayout
        internal var conQuestion: ConstraintLayout
        internal var conParentAnswer: ConstraintLayout
        init {
            imgheadset = view.findViewById<View>(R.id.imgheadset) as ImageView
            menu = view.findViewById<View>(R.id.menu) as ImageView
            txtMyMessage = view.findViewById<View>(R.id.txtMyMessage) as TextView
            txtMyMessageTime = view.findViewById<View>(R.id.txtMyMessageTime) as TextView
            lblParentReciver = view.findViewById<View>(R.id.lblParentReciver) as TextView
            txtParentTime1 = view.findViewById<View>(R.id.txtParentTime1) as TextView
            lblstaffname = view.findViewById<View>(R.id.lblstaffname) as TextView
            lblstaffAnsname = view.findViewById<View>(R.id.lblstaffAnsname) as TextView
            conAns = view.findViewById<View>(R.id.conAns) as ConstraintLayout
            conQuestion = view.findViewById<View>(R.id.conQuestion) as ConstraintLayout
            conParentAnswer = view.findViewById<View>(R.id.conParentAnswer) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_sender_design, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        holder.conQuestion.visibility=View.GONE
        holder.imgheadset.setImageResource(R.drawable.prnt_group)
        holder.lblstaffname.setText(text_info.student_name)
        holder.lblstaffAnsname.setText(staffname)
        holder.txtMyMessage.setText(text_info.question)
        holder.txtMyMessageTime.setText(text_info.created_on)
        if(text_info.answered_on.equals("")){
            holder.conParentAnswer.visibility=View.GONE
        }
        else {
            holder.conParentAnswer.visibility=View.VISIBLE
            holder.lblParentReciver.setText(text_info.answer)
            holder.txtParentTime1.setText(text_info.answered_on)
        }

    }
    override fun getItemCount(): Int {
        return listname.size

    }
}



