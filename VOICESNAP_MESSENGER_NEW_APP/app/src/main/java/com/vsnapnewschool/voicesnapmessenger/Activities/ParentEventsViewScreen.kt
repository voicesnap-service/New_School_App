package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.EventsData
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.UpcomingEventsResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_event_history.*

class ParentEventsViewScreen : BaseActivity (),View.OnClickListener{
    var EventData: EventsData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_event_history_scroll)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Events))
        enableSearch(false)
        scrollAdds(this,imageSlider)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        val type: String = intent.getStringExtra("type")!!
        if (type.equals("0")) {
            layoutCreatedBy.visibility = View.VISIBLE
        }
        EventData = intent.getSerializableExtra("EventClass") as? EventsData?

        lblEventTitle.text=EventData!!.title
        lblCreatedBy.text=EventData!!.created_by
        lblEventDay.text=EventData!!.event_date
        lblEventTime.text=EventData!!.event_time
        lblEventDetails.text=EventData!!.description
        lblName.text=EventData!!.created_by_short


        if(EventData!!.is_photo_exists.equals("0")){
            rytViewDownload.visibility=View.GONE
        }else{
            rytViewDownload.visibility=View.VISIBLE

        }
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
