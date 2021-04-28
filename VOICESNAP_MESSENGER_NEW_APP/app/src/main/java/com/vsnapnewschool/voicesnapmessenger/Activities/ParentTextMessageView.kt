package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentTextMessageAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetTextMessagesCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.TextMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextMessages
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class ParentTextMessageView : BaseActivity(),View.OnClickListener,GetTextMessagesCallBack {
    internal lateinit var parentTextMessageAdapter: ParentTextMessageAdapter
    var textMessageList = ArrayList<GetTextData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        enableSearch(true)
        setTitle(getString(R.string.title_Text))
        parent_bottom_layout.visibility= View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        StudentAPIServices.getTextMessages(this@ParentTextMessageView,this)

    }

    override fun onResume() {
        super.onResume()

    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgchat -> {
            }
            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(this)
            }
            R.id.imgSettings -> {
                UtilConstants.imgProfileIntent(this)
            } }
    }

    override fun callBackTextMessages(responseBody: GetTextMessages) {

        textMessageList.clear()
        textMessageList= responseBody!!.data as ArrayList<GetTextData>
        parentTextMessageAdapter = ParentTextMessageAdapter(textMessageList, this,object : TextMessagesClickListener {
            override fun onTextClick(holder: ParentTextMessageAdapter.MyViewHolder, text_info: GetTextData) {
                holder.lblmessage.setOnClickListener({
                      UtilConstants.viewMessagePopup(this@ParentTextMessageView,text_info)
                })
            }
        })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = parentTextMessageAdapter

    }
}