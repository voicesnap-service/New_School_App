package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ChatMembersAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.chatmemberListener
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffChatClassDetail
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_chatmember.*
import java.util.*

class ChatMemberProfile : BaseActivity(),View.OnClickListener {
    internal lateinit var chatmemberAdapter: ChatMembersAdapter
    var StaffClassDetails = ArrayList<StaffChatClassDetail>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatmember)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Chat))
        enableSearch(true)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        chatmemberAdapter = ChatMembersAdapter(StaffClassDetails, this, false,object : chatmemberListener {
            override fun onchatclickListener(holder: ChatMembersAdapter.MyViewHolder, text_info: StaffChatClassDetail) {
                holder.overall.setOnClickListener({
                    val context=holder.overall.context

                    val intent = Intent( context, ChatConversation::class.java)
//                    intent.putExtra("type",false)
                    context.startActivity(intent)
                })


            }
        })
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerChat.layoutManager = mLayoutManager
        recyclerChat.itemAnimator = DefaultItemAnimator()
        recyclerChat.adapter = chatmemberAdapter
        chatmemberAdapter.notifyDataSetChanged()


    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }



//    private fun ContentMethod() {
//        val Icons = intArrayOf(R.drawable.man, R.drawable.man, R.drawable.album9)
//        var menus = class_chat("Syaid Editaz", "Not able to get the syllaby",  Icons[0],"5 mins")
//        menulist.add(menus)
//        menus = class_chat("Sanjita Akter", "Chat is Closed",  Icons[1],"15 min")
//        menulist.add(menus)
//        menus = class_chat("Sayed Eftioz", "Can you Please Share the name  ", Icons[2], "1hour")
//        menulist.add(menus)
//    }

}
