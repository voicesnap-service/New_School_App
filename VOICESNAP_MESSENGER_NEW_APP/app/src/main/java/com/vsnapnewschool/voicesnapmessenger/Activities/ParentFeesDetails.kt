package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.google.android.material.tabs.TabLayout
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentFeesTabAdapter
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_view_pager.*

class ParentFeesDetails : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_view_pager)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Fees))
        enableSearch(true)
        scrollAdds(this,imageSlider)

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_fees_due))
        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_upcoming_fee))
        sliding_tabs!!.addTab(sliding_tabs!!.newTab().setText(R.string.tab_paid_fee))
        sliding_tabs!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ParentFeesTabAdapter(this, supportFragmentManager, sliding_tabs!!.tabCount)
        viewPager!!.adapter = adapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(sliding_tabs))
        sliding_tabs!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.currentItem = tab.position
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
