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
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentChatScreen : BaseActivity(),View.OnClickListener {
    internal lateinit var chatMembersAdapter: ChatMembersAdapter
    private val menulist = ArrayList<class_chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_Chat))
        enableSearch(true)
        ContentMethod()
        parent_bottom_layout.visibility= View.VISIBLE
        parent_bottom_layout.visibility= View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        chatMembersAdapter = ChatMembersAdapter(menulist, this, false,object : chatmemberListener {
            override fun onchatclickListener(holder: ChatMembersAdapter.MyViewHolder, text_info: class_chat) {
                holder.overall.setOnClickListener({
                    val context=holder.overall.context
                    UtilConstants.parentChatMemberActivity(context)
                })
            }
        })
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = chatMembersAdapter
        chatMembersAdapter.notifyDataSetChanged()
    }
    private fun ContentMethod() {
        val Icons = intArrayOf(R.drawable.album6, R.drawable.man, R.drawable.album9)
        var menus = class_chat("John Mecancy", "Teacher", Icons[0], "5 mins")
        menulist.add(menus)
        menus = class_chat("Peter Berg", "Teacher", Icons[1], "15 min")
        menulist.add(menus)
        menus = class_chat("Gopi", "Principal", Icons[2], "1hour")
        menulist.add(menus)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgchat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(this)
            }
            R.id.imgSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }
}
