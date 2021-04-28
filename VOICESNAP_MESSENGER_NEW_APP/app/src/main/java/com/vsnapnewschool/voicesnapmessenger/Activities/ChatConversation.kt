package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ChatConversationAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_chat_screen_messsage.*
import java.util.*

class ChatConversation : BaseActivity(), View.OnClickListener {
    internal lateinit var chatConversationAdapter: ChatConversationAdapter
    private val menulist = ArrayList<class_chat>()
    override fun onCreate(savedInstanceState: Bundle?) {

        val type = intent.extras!!.getBoolean("type")
        if(type){
            setTheme(R.style.AppTheme)

        }else{
            setTheme(R.style.AppThemeParent)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen_messsage)
        enableCrashLytics()
        ContentMethod()
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)


        if(type){
            initializeActionBar()
            setTitle(getString(R.string.title_Chat))
            enableSearch(true)
            imgSend.setImageResource(R.drawable.teacherchat_send)

        }else{
            parentActionbar()
            setTitle(getString(R.string.title_Chat))
            enableSearch(true)
            imgSend.setImageResource(R.drawable.parentchat_send)

        }
        chatConversationAdapter = ChatConversationAdapter(menulist,this,type)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerChat.layoutManager = mLayoutManager
        recyclerChat.itemAnimator = DefaultItemAnimator()
        recyclerChat.adapter = chatConversationAdapter
        chatConversationAdapter.notifyDataSetChanged()
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
    private fun ContentMethod() {
        val Icons = intArrayOf(R.drawable.album6, R.drawable.man, R.drawable.album9)
        var menus = class_chat("Syaid Editaz", "Not able to get the syllabus",  Icons[0],"5 mins")
        menulist.add(menus)
        menus = class_chat("Sanjita Akter", "Chat is Closed",  Icons[1],"15 min")
        menulist.add(menus)
        menus = class_chat("Raji", "Can you Please Share the name  ", Icons[2], "1hour")
        menulist.add(menus)
        menus = class_chat("Raj", "What is the syllabus for exam", Icons[2], "1hour")
        menulist.add(menus)
    }


}
