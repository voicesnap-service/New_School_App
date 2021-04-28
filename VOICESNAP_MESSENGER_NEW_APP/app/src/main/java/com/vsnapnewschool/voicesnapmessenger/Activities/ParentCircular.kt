package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.CircularHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.circularHistorylistener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*


class ParentCircular : BaseActivity(),View.OnClickListener {
    private val menulist = ArrayList<Text_Class>()

    internal lateinit var circularHistoryadapter: CircularHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_Circular))
        enableSearch(true)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        ImageLength()
        parent_bottom_layout.visibility= View.VISIBLE
        circularHistoryadapter = CircularHistoryAdapter(menulist, this,"0", object : circularHistorylistener {
            override fun oncircularclickListener (holder: CircularHistoryAdapter.MyViewHolder, text_info: Text_Class) {
                holder.CircularLayout.setOnClickListener({
                    UtilConstants.parentCircularView(this@ParentCircular,text_info)
                })
            }
        })
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = circularHistoryadapter

    }

    private fun ImageLength() {
        menulist.clear()
        var menus = Text_Class("Last day for the school bus fee for this first term is on july 09 2020 ", "Bus Fees Due", "Sent Successfully", "1:00 PM,Jun 05")
        menulist.add(menus)
        menus = Text_Class("Last day for the school bus fee for this first term is on july 09 2020 ", "Excursion Form", "Sent Successfully", "2:00 PM,Oct 05")
        menulist.add(menus)
        menus = Text_Class("Last day for the school", "Book Fees Due", "Sent Successfully", "3:00 PM,Dec 05")
        menulist.add(menus)
        menus = Text_Class("Last day for the school bus fee for this first term is on july 09 2020 ", "Canteen Fess", "Sent Successfully", "Jun 05 at 10.30 am")
        menulist.add(menus)
        menus = Text_Class("Last day for the school bus fee for this first term is on july 09 2020 ", "Tution Fees", "Sent Successfully", "Jun 05 at 10.30 am")
        menulist.add(menus)

    }
    fun filter(s: String) {
        val assignment: ArrayList<Text_Class> = ArrayList<Text_Class>()
        for (d in menulist) {
            val value: String =
                d.recipients!!.toLowerCase() + d.content!!.toLowerCase() + d.status!!.toLowerCase() + d.time!!.toLowerCase()
            if (value.contains(s.toLowerCase())) {
                assignment.add(d)
                lblNoRecordsFound.setVisibility(View.GONE)
            } else if (!value.contains(s) && assignment.size == 0) {
                lblNoRecordsFound.setVisibility(View.VISIBLE)
            }
        }
        if (menulist.size != 0)
            circularHistoryadapter.update(assignment);
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
        }    }

}

