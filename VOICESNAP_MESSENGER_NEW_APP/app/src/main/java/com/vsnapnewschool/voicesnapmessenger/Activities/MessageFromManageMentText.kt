package com.vsnapnewschool.voicesnapmessenger.Activities

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManagementTextAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentTextMessageAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.MsgFromManagementCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.TextMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementTextListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetTextData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementTextResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class MessageFromManageMentText : BaseActivity(), View.OnClickListener, MsgFromManagementCallBack, ReadStatusCallBacak {
    internal lateinit var parentTextMessageAdapter: ManagementTextAdapter
    var textMessageList = ArrayList<MessageFromManagementTextResponse.TextData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this, imageSlider)
        initializeActionBar()
        enableSearch(true)
        setTitle(getString(R.string.title_Text))
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        lblSeeMore?.setOnClickListener(this)
        lblSeeMore.setTextColor(Color.parseColor("#fb6b22"))
        UtilConstants.CLICKED_SEE_MORE =false
        TeacherBottomLayout.visibility=View.VISIBLE
        SchoolAPIServices.getMessageFromManagementTextMessage(this, this,
            UtilConstants.API_NORMAL,recyclerview,shimmerFrameLayout)

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
        menuInfo: ContextMenu.ContextMenuInfo?) {
        menu.add(0, v.id, 0, "Copy")
        val textView = v as TextView
        val manager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textView.text)
        manager.setPrimaryClip(clipData)
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
            R.id.lblSeeMore -> {
                SchoolAPIServices.getMessageFromManagementTextMessage(
                    this,
                    this,
                    UtilConstants.API_SEE_MORE,
                    recyclerview,
                    shimmerFrameLayout
                )
            }
        }
    }




    private fun getTextMessagesAfterReadList() {
        SchoolAPIServices.getMessageFromManagementTextMessage(
            this,
            this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )
    }

    override fun callBackReadStatus(updateStatus: Boolean?) {
        if(updateStatus == true){
            getTextMessagesAfterReadList()
        }
    }

    override fun callBackManagementText(responseBody: MessageFromManagementTextResponse) {

        textMessageList.clear()
        textMessageList= responseBody.data as ArrayList<MessageFromManagementTextResponse.TextData>
        parentTextMessageAdapter = ManagementTextAdapter(textMessageList, this,
            object : managementTextListener {
                override fun onManagementTextClick(
                    holder: ManagementTextAdapter.MyViewHolder,
                    text_info: MessageFromManagementTextResponse.TextData
                ) {
                    holder.lblContainer.setOnClickListener({
                        viewMessagePopup(this@MessageFromManageMentText, text_info)
                        if(text_info.is_archive == 0){
                            StudentAPIServices.updateReadStatus(this@MessageFromManageMentText,text_info.header_id,text_info.detail_id, UtilConstants.API_NORMAL,this@MessageFromManageMentText)
                        }
                        else{
                            StudentAPIServices.updateReadStatus(this@MessageFromManageMentText,text_info.header_id,text_info.detail_id, UtilConstants.API_SEE_MORE,this@MessageFromManageMentText)
                        }
                    })
                }

            })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = parentTextMessageAdapter

        if(UtilConstants.CLICKED_SEE_MORE == true){
            SchoolAPIServices.getMessageFromManagementTextMessage(
                this@MessageFromManageMentText,
                this,
                UtilConstants.API_SEE_MORE,
                recyclerview,
                shimmerFrameLayout
            )
        }
    }

    override fun callBackManagementText_Archive(responseBody: MessageFromManagementTextResponse) {
        UtilConstants.CLICKED_SEE_MORE =true
        lblSeeMore.visibility= View.GONE
        textMessageList.addAll(responseBody.data)
        parentTextMessageAdapter.notifyDataSetChanged()
    }


    fun viewMessagePopup(activity: Activity?, text_info: MessageFromManagementTextResponse.TextData) {
        val inflater: LayoutInflater =
            activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater.inflate(R.layout.message_view_popup, null)
        val popupWindow = PopupWindow(
            view,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )

        popupWindow.setContentView(view)
        popupWindow.setTouchable(true)
        popupWindow.setFocusable(true)
        popupWindow.setOutsideTouchable(false)
        val lblMessage = view.findViewById<TextView>(R.id.lblMessage)
        val lblCreatedBy = view.findViewById<TextView>(R.id.lblCreatedBy)
        val imgClose = view.findViewById<ImageView>(R.id.imgClose)

        lblCreatedBy.setText("Created By  :  " + text_info.created_by)
        lblMessage.setText(text_info.content)
        imgClose.setOnClickListener {
            popupWindow.dismiss()
        }
        popupWindow.animationStyle = R.style.AnimationPopup
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
    }

}










