package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Activities.ParentChatScreen
import com.vsnapnewschool.voicesnapmessenger.Interfaces.chatmemberListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentchatmemberListener
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StaffChatDetails
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentStaffChatResponse
import com.vsnapnewschool.voicesnapmessenger.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
class ParentChatMembersAdapter(private val listname: ArrayList<StaffChatDetails>, private val context: Context, private  val type :Boolean,
                               val chatListener: parentchatmemberListener
) : RecyclerView.Adapter<ParentChatMembersAdapter.MyViewHolder>() {

    companion object {
        var chatmemberListener: parentchatmemberListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblPersonName: TextView
        internal var lblContentMsg: TextView
        internal var lblTiming: TextView
        internal var overall: RelativeLayout
        internal var imgMember: CircleImageView
        internal var imgChat: ImageView
        init {
            lblPersonName = view.findViewById<View>(R.id.lblPersonName) as TextView
            lblContentMsg = view.findViewById<View>(R.id.lblContentMsg) as TextView
            lblTiming = view.findViewById<View>(R.id.lblTiming) as TextView
            overall = view.findViewById<View>(R.id.rytOverall) as RelativeLayout
            imgMember = view.findViewById<View>(R.id.imgMember) as CircleImageView
            imgChat = view.findViewById<View>(R.id.imgChat) as ImageView

        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_member_screen, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val text_info = listname[position]
        chatmemberListener = chatListener
        chatListener.onchatclickListener(holder,text_info)
        holder.lblContentMsg.text = text_info.subject_name
        holder.lblPersonName.text = text_info.staff_name
        Glide.with(context)
            .load(R.drawable.img_profile)
            .into(holder.imgMember);



    }
    override fun getItemCount(): Int {
        return listname.size
    }
}



