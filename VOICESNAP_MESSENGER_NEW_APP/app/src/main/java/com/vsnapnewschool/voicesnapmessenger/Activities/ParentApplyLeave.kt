package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentApplyLeaveTabAdapter
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_view_pager.*

class ParentApplyLeave : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_view_pager)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_leave))

        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_leave_request))
        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_manage_leave))
        sliding_tabs!!.tabGravity = TabLayout.GRAVITY_FILL

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        adds_layout.visibility=View.GONE

        val adapter = ParentApplyLeaveTabAdapter(this, supportFragmentManager, sliding_tabs!!.tabCount)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(sliding_tabs))
        sliding_tabs!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
                if (tab.position == 0) {
                    enableSearch(false)

                    HideKeyboard_Fragment(this@ParentApplyLeave)
                } else {
                    enableSearch(true)
                    HideKeyboard_Fragment(this@ParentApplyLeave)

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

