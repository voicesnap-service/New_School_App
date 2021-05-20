package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ManagementCountCallBack
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetCountForManagement
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountImage
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountPdf
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountText
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountVideo
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ManagementCountVoice
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences.Companion.getRazorPayAPIKey
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences.Companion.getTextCount
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_message_from_mangement_menu_screen.*

class MessageFromMangementMenuScreen : BaseActivity(),View.OnClickListener{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_from_mangement_menu_screen)

        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_msgfrom_management))
        enableSearch(false)
        SchoolAPIServices.getCountForManagement(this)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        LayoutText?.setOnClickListener(this)
        LayoutVoice?.setOnClickListener(this)
        LayoutImage?.setOnClickListener(this)
        LayoutPdf?.setOnClickListener(this)
        LayoutVideo?.setOnClickListener(this)


        val textCount = Util_shared_preferences.getTextCount(this)
        val voice = Util_shared_preferences.getVoiceCount(this)
        val image = Util_shared_preferences.getImageCount(this)
        val pdf = Util_shared_preferences.getPdfCount(this)
        val video = Util_shared_preferences.getVideoCount(this)

        lblUnreadTextCount.text=textCount
        lblUnreadCountVoice.text=voice
        lblUnreadCountImage.text=image
        lblUnreadCountPdf.text= pdf
        lblUnreadCountVideo.text= video

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
            R.id.LayoutText -> {
                UtilConstants.TextmessageFromManagement(this)
            }
            R.id.LayoutVoice -> {

                UtilConstants.VoiceFromManagement(this)

            }

            R.id.LayoutVideo -> {

                UtilConstants.VideoFromManagement(this)

            }
            R.id.LayoutImage -> {
                UtilConstants.ImagesFromManagement(this)


            }

            R.id.LayoutPdf -> {

                UtilConstants.PdfFromManagementScreen(this)

            }

        }
    }
//
//    override fun callBackCount(responseBody: GetCountForManagement) {
//        ManagementCountText = responseBody.data[0].sms_count
//        Log.d("ManagementCountText",ManagementCountText!!)
//        ManagementCountVoice = responseBody.data[0].voice_count
//        Log.d("ManagementCountVoice",ManagementCountVoice!!)
//        ManagementCountImage = responseBody.data[0].image_count
//        ManagementCountPdf = responseBody.data[0].pdf_count
//        ManagementCountVideo = responseBody.data[0].video_count
//
//
//
//    }
}