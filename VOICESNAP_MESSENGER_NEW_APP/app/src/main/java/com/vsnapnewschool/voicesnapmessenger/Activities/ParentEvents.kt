package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentEventsAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.eventsparentListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentEvents : BaseActivity(),View.OnClickListener  {
    private val menulist = ArrayList<EventsImageClass>()
    internal lateinit var eventsAdapter: ParentEventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Events))
        enableSearch(true)
        scrollAdds(this,imageSlider)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        parent_bottom_layout.visibility= View.VISIBLE
        ImageLength()
        eventsAdapter = ParentEventsAdapter(menulist, this,true, object : eventsparentListener {
            override fun oneventClick(holder: ParentEventsAdapter.MyViewHolder, text_info: EventsImageClass) {
                holder.imgEventsImage.setOnClickListener({
                    UtilConstants.parentEventsHistoryActivity(this@ParentEvents)
                })
            }
        })
        val mLayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = eventsAdapter

    }
    private fun ImageLength() {
        var movieModel = EventsImageClass(R.drawable.event1,"05","Aug","AnnualDay 2020","Main Audiotorium","")
        menulist.add(movieModel)

        movieModel = EventsImageClass(R.drawable.event2,"30","Jun","AnnualDay 2020","Main Audiotorium","")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.event3,"09","Sep","AnnualDay 2020","Main Audiotorium","")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.event4,"06","Aug","AnnualDay 2020","Main Audiotorium","")
        menulist.add(movieModel)
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