package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.vsnapnewschool.voicesnapmessenger.Adapters.VoiceTabAdapter
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_view_pager.*


class TeacherVoiceMessages : BaseActivity(), View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)
        enableCrashLytics()
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        initializeActionBar()
        setTitle(getString(R.string.title_Voice))
        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_voice_record))
        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_history))
        sliding_tabs!!.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = VoiceTabAdapter(this, supportFragmentManager, sliding_tabs!!.tabCount)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(sliding_tabs))
        sliding_tabs!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position

                if(tab.position==0){
                    HideKeyboard_Fragment(this@TeacherVoiceMessages)

                    enableSearch(false)
                } else{
                    HideKeyboard_Fragment(this@TeacherVoiceMessages)

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
