package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentNoticeBoardAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.noticeboardListener
import com.vsnapnewschool.voicesnapmessenger.Models.Image_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentNoticeBoard : BaseActivity(),View.OnClickListener  {
    private val menulist = ArrayList<Image_Class>()
    internal lateinit var NoticeBoardAdapter: ParentNoticeBoardAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_notice_board))
        enableSearch(true)
        parent_bottom_layout.visibility= View.VISIBLE
        ContentMethod()
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        NoticeBoardAdapter = ParentNoticeBoardAdapter(menulist, this,"0", object : noticeboardListener {
            override fun noticeboardClick (holder: ParentNoticeBoardAdapter.MyViewHolder, text_info: Image_Class) {
                holder.rytHistory.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View) {

                        val pos = holder.adapterPosition
                       // val context=holder.rytHistory.context
                        UtilConstants.parentNoticeboardHistoryActivity(this@ParentNoticeBoard)


                    }
                })
            }
        })
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = NoticeBoardAdapter

    }
    private fun ContentMethod() {
        var menus = Image_Class("Sent on Jun 05 at 10.30 am", "Bus Fees Due", "Kotlin has great support and many contributions from the community, which is growing all over the world.Java has great support and many contributions from the community, which is growing all over the world")
        menulist.add(menus)
        menus = Image_Class("Sent on Dec 31 at 10.30 am", "Excursion Fees Due", "Java has great support and many contributions from the community, which is growing all over the world")
        menulist.add(menus)
        menus = Image_Class("Sent on Feb 19 at 10.30 am", "Book Fees Due", "Android has great support and many contributions from the community, which is growing all over the world")
        menulist.add(menus)
        menus = Image_Class("Sent on Oct 09 at 10.30 am", "Exam Fees Due", "Kotlin has great support and many contributions from the community, which is growing all over the world")
        menulist.add(menus)
        menus = Image_Class("Sent on Jun 05 at 10.30 am", "Bus Fees Due", "Kotlin has great support and many contributions from the community, which is growing all over the world")
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