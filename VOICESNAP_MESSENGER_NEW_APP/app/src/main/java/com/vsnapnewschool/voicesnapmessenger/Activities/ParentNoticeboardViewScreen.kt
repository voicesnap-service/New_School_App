package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetNoticeBoardResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_noticeboard_history.*
import kotlinx.android.synthetic.main.parent_noticeboard_history.lblHeading


class ParentNoticeboardViewScreen : BaseActivity(), View.OnClickListener {

    var NoticeboardData: GetNoticeBoardResponse.NoticeData? = null
     var type:String?=null
     var position:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_noticeboard_history)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_notice_board))
        enableSearch(false)

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

         type = intent.getStringExtra("type")!!
        position = intent.getIntExtra("position",0)
        if (type.equals("0")) {
            lblname.visibility = View.GONE
            lblRecipients.visibility = View.GONE
            rytViewNotice.setBackgroundResource(R.drawable.parent_blue_bg)
        }


        NoticeboardData =
            intent.getSerializableExtra("Noticeboard") as GetNoticeBoardResponse.NoticeData?

        lblHeading.text = NoticeboardData!!.title
        lblHistoryNotice.text = NoticeboardData!!.description
        lblTimingNotice.text = NoticeboardData!!.created_on
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
            R.id.rytViewNotice -> {
                UtilConstants.viewFileNotice(this,NoticeboardData!!,true,position!!)
            }
        }
    }

}
