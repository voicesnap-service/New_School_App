package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.VoiceFilePath
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.voicehistorygroup
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_voice.*
import kotlinx.android.synthetic.main.record_voice.*
import kotlinx.android.synthetic.main.scroll_preview_voice.*
import me.jagar.chatvoiceplayerlibrary.FileUtils
import java.io.File

class FinalPreviewVoiceHistory : BaseActivity(), View.OnClickListener {
    var mediaPlayer: MediaPlayer? = null
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var iMediaDuration = 0
    var iMaxRecDur = 180
    var recTime = 0
    var recTimerHandler = Handler()
    var VoiceType: Boolean? = null

    lateinit var constScheduleDate : ConstraintLayout
    lateinit var lblEndDate : TextView
    lateinit var lblSelectEndTime : TextView
    lateinit var lblSelectTime : TextView
    lateinit var lblDate : TextView
    lateinit var btnschedule : TextView
    lateinit var btnsend : TextView
    lateinit var rytMonth : RelativeLayout
    lateinit var rytTime : RelativeLayout
    lateinit var rytEndMonth : RelativeLayout
    lateinit var rytEndTime : RelativeLayout
    lateinit var lnrVoiceButtons : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_voice)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
//        constScheduleDate = findViewById<ConstraintLayout>(R.id.constScheduleDate)
        lblEndDate = findViewById<TextView>(R.id.lblEndDate)
        lblDate = findViewById<TextView>(R.id.lblDate)
        lblSelectTime = findViewById<TextView>(R.id.lblSelectTime)
        lblSelectEndTime = findViewById<TextView>(R.id.lblSelectEndTime)
        btnschedule = findViewById<TextView>(R.id.btnschedule)
        btnsend= findViewById<TextView>(R.id.btnsend)
        rytMonth= findViewById<RelativeLayout>(R.id.rytMonth)
        rytTime= findViewById<RelativeLayout>(R.id.rytTime)
        rytEndMonth= findViewById<RelativeLayout>(R.id.rytEndMonth)
        rytEndTime= findViewById<RelativeLayout>(R.id.rytEndTime)
        lnrVoiceButtons= findViewById<LinearLayout>(R.id.lnrVoiceButtons)
        enableCrashLytics()
        initializeActionBar()
        enableSearch(false)
        imgplay?.setOnClickListener(this)
        lblVoiceTitle.text = UtilConstants.Title
        lblSentAt
        finalPreviewReceipientsAdpter(this)
        btnPublish?.setOnClickListener(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnschedule?.setOnClickListener(this)
        btnsend?.setOnClickListener(this)
        rytMonth?.setOnClickListener(this)
        rytTime?.setOnClickListener(this)
        rytEndMonth?.setOnClickListener(this)
        rytEndTime?.setOnClickListener(this)

        VoiceType = intent.extras!!.getBoolean("Voicetype")
        Util_shared_preferences.putVoiceType(this,VoiceType!!)
//        if(voicehistorygroup.equals("VoiceHistoryGroup")){
//            rcyleRecipients!!.visibility = View.VISIBLE
//            lblrecipient!!.visibility = View.VISIBLE
//            setTitle(getString(R.string.forward))
//            btnPublish.text = getString(R.string.btn_Publish)
//        }
        if (VoiceType as Boolean) {
            setTitle(getString(R.string.forward))
            btnPublish.text = getString(R.string.forward)
            btnPublish.isEnabled = true
            lblSentAt.visibility = View.VISIBLE
            ReciptCount.visibility = View.GONE
            lblrecipient!!.visibility = View.GONE
            rcyleRecipients!!.visibility = View.GONE

        } else {
            setTitle(getString(R.string.txt_Preview))
            btnPublish.text = getString(R.string.btn_Publish)
            btnPublish.isEnabled = true
            lblSentAt.visibility = View.INVISIBLE


        }
        if(UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE){
            lnrVoiceButtons.visibility=View.VISIBLE

        }else{
            lnrVoiceButtons.visibility=View.GONE

        }
        setupAudioPlayer()
        fetchSong()


    }

    fun fetchSong() {

        try {
//            val filepath: String
//            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//             //   filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()
//                filepath=this.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()
//
//            } else {
//
//                filepath = Environment.getExternalStorageDirectory().getPath();
//            }
//
//            val fileDir = File(filepath, VOICE_FOLDER_NAME)
//
//            if (!fileDir.exists()) {
//                fileDir.mkdirs()
//            }
//
//            futureStudioIconFile = File(fileDir, VOICE_FILE_NAME)
//            VoiceFilePath = futureStudioIconFile?.path
//            Log.d("VoiceFilePath",VoiceFilePath!!)
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(VoiceFilePath)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()
//            UtilConstants.VoiceDuration = iMediaDuration.toString()
            playseekbar.setVisibility(View.VISIBLE)
            playseekbar.getProgressDrawable().setColorFilter(
                this.getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN
            )
            playseekbar.getThumb().setColorFilter(
                this.getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN
            )
            playseekbar.setMax(mediaPlayer!!.getDuration())
            playseekbar.setProgress(0)
            playseekbar.updatePlayerPercent(0F)
            playseekbar.updateVisualizer(FileUtils.fileToBytes(File(VoiceFilePath)))
            playseekbar.setColors(Color.parseColor("#FFFFFFFF"), Color.parseColor("#7C7C7C"))

        } catch (e: Exception) {
            Log.d("in Fetch Song", e.toString())
        }
        Log.d("FetchSong", "END***************************************")
    }

    private fun setupAudioPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnCompletionListener {
            imgplay.setImageResource(R.drawable.white_pause)
            mediaPlayer!!.seekTo(0)
//            playseekbar.setProgress(0)
//            playseekbar.updatePlayerPercent(0F)
        }
    }

    fun recplaypause() {
        mediaFileLengthInMilliseconds = mediaPlayer!!.duration

        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
            imgplay.setImageResource(R.drawable.white_play)
        } else {
            mediaPlayer!!.pause()
            imgplay.setClickable(true)
            imgplay.setImageResource(R.drawable.white_pause)
        }
        primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
    }


    private fun primarySeekBarProgressUpdater(fileLength: Int) {
        if (mediaPlayer!!.isPlaying) {
            val notification = Runnable {
                primarySeekBarProgressUpdater(fileLength)
                lblvoicetime.setText(milliSecondsToTimer(mediaPlayer!!.currentPosition.toLong()))
                playseekbar.setProgress(mediaPlayer!!.currentPosition)
                playseekbar.updatePlayerPercent(mediaPlayer!!.currentPosition.toFloat() / mediaPlayer!!.duration)
                primarySeekBarProgressUpdater(fileLength)

            }
            handler.postDelayed(notification, 1000)
        } else {
            lblvoicetime.setText("00:00")
            playseekbar.setProgress(0)
            playseekbar.updatePlayerPercent(0F)
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgplay -> {
                recplaypause()
            }
            R.id.btnPublish -> {
                if(UtilConstants.MENU_TYPE == UtilConstants.MENU_EMERGENCY){
                    UtilConstants.voicehistoryentire = "VoiceHistoryEntire"
                    UtilConstants.schoolListScreen(this as Activity?)
                }
                else {
//                    if (voicehistorygroup.equals("VoiceGroup")) {
//                        if (UtilConstants.RecipientsType == UtilConstants.Students) {
//                            SchoolAPIServices.sendNonVoiceFromHistoryToStdSecGrpStaffStud(this)
//                        } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
//                            SchoolAPIServices.sendNonVoiceFromHistoryToStdSecGrpStaffStud(this)
//                        } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
//                            SchoolAPIServices.sendNonVoiceFromHistoryToStdSecGrpStaffStud(this)
//                        } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
//                            SchoolAPIServices.sendNonVoiceFromHistoryToStdSecGrpStaffStud(this)
//                        } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
//                            SchoolAPIServices.sendNonVoiceFromHistoryToStdSecGrpStaffStud(this)
//                        }
//                    } else if (VoiceType!!) {
                    UtilConstants.voicehistoryentire = "VoiceHistoryEntire"
                    UtilConstants.voicehistorygroup = "VoiceHistoryGroup"
                    UtilConstants.recipientsActivity(this)

//                    }
                }

            }
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
            R.id.btnschedule -> {
                UtilConstants.ScheduleType="schedule"
                constScheduleDate.visibility= View.VISIBLE
                btnschedule.setBackground(resources.getDrawable(R.drawable.bg_corners_circle_orange))
                btnsend.setTextColor(R.color.clr_school)
                btnsend.setBackgroundResource(R.drawable.circle_white_orange_send)
                btnschedule.setTextColor(Color.parseColor("#ffffff"))

            }
            R.id.btnsend -> {
                UtilConstants.ScheduleType="instant"
                constScheduleDate.visibility= View.GONE
                btnsend.setBackground(resources.getDrawable(R.drawable.bg_corners_circle_orange))
                btnschedule.setTextColor(R.color.clr_school)
                btnschedule.setBackgroundResource(R.drawable.bg_corners_circle_white)
                btnsend.setTextColor(Color.parseColor("#ffffff"))

            }
            R.id.rytMonth -> {
                this.PickDate(this,lblDate,true,0)
//                activity?.let { it1 -> (activity as BaseActivity?)!!.PickDate(it1,lblDate,true,0) }
            }
            R.id.rytTime -> {
                this.PickTime(this,lblSelectTime,0)

//                activity?.let { it1 -> (activity as BaseActivity?)!!.PickTime(it1, lblSelectTime,0) }

            }
            R.id.rytEndMonth ->{
                this.PickDate(this,lblEndDate,true,1)

//                activity?.let { it1 -> (activity as BaseActivity?)!!.PickDate(it1,lblEndDate,true,1) }
            }
            R.id.rytEndTime ->{
                this.PickTime(this,lblSelectEndTime,1)

//                activity?.let { it1 -> (activity as BaseActivity?)!!.PickTime(it1, lblSelectEndTime,1) }
            }

        }
    }


    fun milliSecondsToTimer(milliseconds: Long): String? {
        var finalTimerString = ""
        var secondsString = ""
        var minutesString = ""
        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        if (hours > 0) {
            finalTimerString = "$hours:"
        }
        minutesString = if (minutes < 10) {
            "0$minutes"
        } else {
            "" + minutes
        }
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutesString:$secondsString"
        return finalTimerString
    }

    val runson: Runnable = object : Runnable {
        override fun run() {
            lbltime.setText(milliSecondsToTimer(recTime * 1000.toLong()))
            val timing: String = lbltime.getText().toString()
            if (lbltime.getText().toString() != "00:00") {
                imgRec.setEnabled(true)
            }
            recTime = recTime + 1
            if (recTime != iMaxRecDur)
                recTimerHandler.postDelayed(this, 1000)
        }
    }
}