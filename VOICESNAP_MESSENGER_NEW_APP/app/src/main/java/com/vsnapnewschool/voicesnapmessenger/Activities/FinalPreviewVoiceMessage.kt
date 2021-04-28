package com.vsnapnewschool.voicesnapmessenger.Activities

import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.VoiceFilePath
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_voice.*
import kotlinx.android.synthetic.main.record_voice.*
import kotlinx.android.synthetic.main.scroll_preview_voice.*
import me.jagar.chatvoiceplayerlibrary.FileUtils
import java.io.File

class FinalPreviewVoiceMessage : BaseActivity(), View.OnClickListener {
    var mediaPlayer: MediaPlayer? = null
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    val VOICE_FOLDER_NAME = "NewSchool"
    val VOICE_FILE_NAME = "schoolVoice.mp3"
    var iMediaDuration = 0
    var futureStudioIconFile: File? = null
    var iMaxRecDur = 180
    var recTime = 0
    var recTimerHandler = Handler()
    var voiceclass: Text_Class? = null
    var FilePath: String? = null
    var VoiceType: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_voice)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
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

        VoiceType = intent.extras!!.getBoolean("Voicetype")

        if (VoiceType as Boolean) {
            setTitle(getString(R.string.forward))
            btnPublish.text = getString(R.string.forward)
            btnPublish.isEnabled = true
            lblSentAt.visibility = View.VISIBLE

        } else {
            setTitle(getString(R.string.txt_Preview))
            btnPublish.text = getString(R.string.btn_Publish)
            btnPublish.isEnabled = true
            lblSentAt.visibility = View.INVISIBLE


        }
        setupAudioPlayer()
        fetchSong()


    }

    fun fetchSong() {

        try {
            val filepath: String
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
             //   filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()
                filepath=this.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()

            } else {

                filepath = Environment.getExternalStorageDirectory().getPath();
            }

            val fileDir = File(filepath, VOICE_FOLDER_NAME)

            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }

            futureStudioIconFile = File(fileDir, VOICE_FILE_NAME)
            VoiceFilePath = futureStudioIconFile?.path
            Log.d("VoiceFilePath",VoiceFilePath!!)
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(futureStudioIconFile!!.path)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()
            UtilConstants.VoiceDuration = iMediaDuration.toString()
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

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgplay -> {
                recplaypause()
            }
            R.id.btnPublish -> {
                if (VoiceType!!) {


                    UtilConstants.recipientsActivity(this)
                } else {
                    if (UtilConstants.RecipientsType == UtilConstants.Students) {
                        SchoolAPIServices.sendNonEmergencyVoiceStd_Sec_Grp_Stud_Satff(this)
                    } else if (UtilConstants.RecipientsType == UtilConstants.Standard) {
                        SchoolAPIServices.sendNonEmergencyVoiceStd_Sec_Grp_Stud_Satff(this)
                    } else if (UtilConstants.RecipientsType == UtilConstants.StandardSection) {
                        SchoolAPIServices.sendNonEmergencyVoiceStd_Sec_Grp_Stud_Satff(this)
                    } else if (UtilConstants.RecipientsType == UtilConstants.Group) {
                        SchoolAPIServices.sendNonEmergencyVoiceStd_Sec_Grp_Stud_Satff(this)
                    } else if (UtilConstants.RecipientsType == UtilConstants.Staff) {
                        SchoolAPIServices.sendNonEmergencyVoiceStd_Sec_Grp_Stud_Satff(this)
                    }
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