package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_noticeboard_history.*


class ParentNoticeboardViewScreen : BaseActivity(),View.OnClickListener {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.parent_noticeboard_history)
            enableCrashLytics()
            scrollAdds(this,imageSlider)
            parentActionbar()
            setTitle(getString(R.string.title_notice_board))
            enableSearch(false)

            imgchat?.setOnClickListener(this)
            imgHomeMenu?.setOnClickListener(this)
            imgSettings?.setOnClickListener(this)

            val type:String = intent.getStringExtra("type")!!
            if(type.equals("0")){
                lblname.visibility= View.GONE
                lblRecipients.visibility= View.GONE
                rytViewNotice.setBackgroundResource(R.drawable.parent_blue_bg)
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
