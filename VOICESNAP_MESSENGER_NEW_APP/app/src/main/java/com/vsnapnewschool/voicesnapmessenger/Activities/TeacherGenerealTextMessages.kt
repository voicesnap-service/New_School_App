package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.vsnapnewschool.voicesnapmessenger.Adapters.TextTabAdapter
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_view_pager.*

class TeacherGenerealTextMessages : BaseActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.text_view_pager)
        enableCrashLytics()
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        initializeActionBar()
        setTitle(getString(R.string.title_Text))
        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_publish))
        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_history))
        sliding_tabs!!.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = TextTabAdapter(this, supportFragmentManager, sliding_tabs!!.tabCount)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(sliding_tabs))

        sliding_tabs!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
                if(tab.position==0){
                    HideKeyboard_Fragment(this@TeacherGenerealTextMessages)
                    enableSearch(false)
                } else{
                    HideKeyboard_Fragment(this@TeacherGenerealTextMessages)
                    enableSearch(true)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {
            }
            override fun onTabReselected(tab: TabLayout.Tab) {
            }
        })
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

