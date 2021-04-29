package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.View
import android.widget.TextView
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
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.API_NORMAL
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.API_SEE_MORE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.CLICKED_SEE_MORE
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentTextMessageView : BaseActivity(),View.OnClickListener,GetTextMessagesCallBack {
    internal lateinit var parentTextMessageAdapter: ParentTextMessageAdapter

    var textMessageList = ArrayList<GetTextData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        parentActionbar()
        enableSearch(true)
        setTitle(getString(R.string.title_Text))
        parent_bottom_layout.visibility= View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        lblSeeMore?.setOnClickListener(this)

        CLICKED_SEE_MORE=false
        StudentAPIServices.getTextMessages(this@ParentTextMessageView, this, API_NORMAL)
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenuInfo?) {
        menu.add(0, v.id, 0, "Copy")
        val textView = v as TextView
        val manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textView.text)
        manager.setPrimaryClip(clipData)
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
            }
            R.id.lblSeeMore -> {
                StudentAPIServices.getTextMessages(this@ParentTextMessageView, this, API_SEE_MORE)
            }
        }
    }
    override fun callBackTextMessages(responseBody: GetTextMessages) {

        shimmerFrameLayout.stopShimmerAnimation()
        textMessageList.clear()
        textMessageList= responseBody!!.data as ArrayList<GetTextData>
        parentTextMessageAdapter = ParentTextMessageAdapter(
            textMessageList,
            this,
            object : TextMessagesClickListener {
                override fun onTextClick(
                    holder: ParentTextMessageAdapter.MyViewHolder,
                    text_info: GetTextData) {
                    holder.lblContainer.setOnClickListener({
                        UtilConstants.viewMessagePopup(this@ParentTextMessageView, text_info)
                        if(text_info.is_archive == 0){
                            StudentAPIServices.updateReadStatus(this@ParentTextMessageView,text_info.header_id,text_info.detail_id, API_NORMAL,this)
                        }
                        else{
                            StudentAPIServices.updateReadStatus(this@ParentTextMessageView,text_info.header_id,text_info.detail_id, API_SEE_MORE,this)
                        }
                    })
                }
                override fun callBackReadStatus(updateStatus: Boolean?) {
                    if(updateStatus == true){
                        getTextMessagesAfterReadList()
                    }
                }
            })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = parentTextMessageAdapter

        if(CLICKED_SEE_MORE == true){
            StudentAPIServices.getTextMessages(this@ParentTextMessageView, this, API_SEE_MORE)

        }
    }

    override fun callBackTextMessages_Archive(responseBody: GetTextMessages) {

        CLICKED_SEE_MORE=true
        lblSeeMore.visibility=View.GONE
        textMessageList.addAll(responseBody.data)
        parentTextMessageAdapter.notifyDataSetChanged()
    }

    private fun getTextMessagesAfterReadList() {
        StudentAPIServices.getTextMessages(this@ParentTextMessageView, this, API_NORMAL)
    }
}