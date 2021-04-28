package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ChatMembersAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.chatmemberListener
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class TeacherChatScreen : BaseActivity(),View.OnClickListener{
    internal lateinit var ChatMemberAdapter: ChatMembersAdapter
    private val menulist = ArrayList<class_chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_Chat))
        enableSearch(true)
        ContentMethod()
        TeacherBottomLayout.visibility=View.VISIBLE
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        TeacherBottomLayout.visibility= View.VISIBLE
        parent_bottom_layout.visibility= View.GONE
        adds_layout.visibility= View.GONE

        ChatMemberAdapter = ChatMembersAdapter(menulist, this, false,object : chatmemberListener {
            override fun onchatclickListener(holder: ChatMembersAdapter.MyViewHolder, text_info: class_chat) {
                holder.overall.setOnClickListener({
                    val context=holder.overall.context
                    UtilConstants.teacherChatActivity(this@TeacherChatScreen)

                })
            }
        })
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = ChatMemberAdapter
        ChatMemberAdapter.notifyDataSetChanged()

    }
    private fun ContentMethod() {
        val Icons = intArrayOf(
            R.drawable.album6,
            R.drawable.man,
            R.drawable.album9)
        var menus = class_chat("John Mecancy",
            "Teacher",
            Icons[0],
            "5 mins")
        menulist.add(menus)

        menus = class_chat("Peter Berg",
            "Teacher",
            Icons[1],
            "15 min")
        menulist.add(menus)

        menus = class_chat("Gopi",
            "Principal",
            Icons[2],
            "1hour")
        menulist.add(menus)

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
}
