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
import com.vsnapnewschool.voicesnapmessenger.Network.APIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.VoiceFilePath
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_final_preview_homework.*
import kotlinx.android.synthetic.main.activity_final_preview_homework.LayoutVoicePlay
import kotlinx.android.synthetic.main.activity_final_preview_homework.playseekbar
import kotlinx.android.synthetic.main.scroll_preview_homework.*
import me.jagar.chatvoiceplayerlibrary.FileUtils
import java.io.File

class FinalPreviewHomework : BaseActivity(), View.OnClickListener {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scroll_preview_homework)
        lblrecipient = findViewById<TextView>(R.id.lblrecipient)
        rcyleRecipients = findViewById<RecyclerView>(R.id.rcyleRecipients)
        enableCrashLytics()


        imgplaypause?.setOnClickListener(this)
        btnHomeWorkPublish?.setOnClickListener(this)
        initializeActionBar()
        setTitle(getString(R.string.txt_Preview))
        enableSearch(false)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        lblhomeworkTitle.text = UtilConstants.Title
        lblHomeworkDescription.text = UtilConstants.Description
        lblSubject.text = UtilConstants.selectedSubjectName
        rcyleRecipients!!.visibility=View.GONE

      //  finalPreviewReceipientsAdpter(this)

        lblRecipients.setText(getString(R.string.lbl_selectedSubject))

        lblselectedSubject!!.text = UtilConstants.selectedSubjectName

        if (UtilConstants.MENU_TYPE == UtilConstants.MENU_TEXT_HOMEWORK) {
            LayoutVoicePlay.setVisibility(View.GONE)
        } else {
            LayoutVoicePlay.setVisibility(View.VISIBLE)
            lblHomeworkDescription.visibility = View.GONE
            homework.visibility = View.GONE
            setupAudioPlayer()
            fetchSong()
        }
    }

    fun fetchSong() {
        try {
            val filepath: String
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filepath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()
            } else {
                filepath = Environment.getExternalStorageDirectory().getPath();
            }
            val fileDir = File(filepath, VOICE_FOLDER_NAME)
            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }

            futureStudioIconFile = File(fileDir, VOICE_FILE_NAME)
            UtilConstants.VoiceFilePath = futureStudioIconFile?.path
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
            playseekbar.updateVisualizer(FileUtils.fileToBytes(File(UtilConstants.VoiceFilePath)))
            playseekbar.setColors(Color.parseColor("#FFFFFFFF"), Color.parseColor("#7C7C7C"))

        } catch (e: Exception) {
            Log.d("in Fetch Song", e.toString())
        }
        Log.d("FetchSong", "END***************************************")
    }

    private fun setupAudioPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnCompletionListener {
            imgplaypause!!.setImageResource(R.drawable.white_pause)
            mediaPlayer!!.seekTo(0)
            playseekbar.setProgress(0)
            playseekbar.updatePlayerPercent(0F)
        }
    }

    fun recplaypause() {
        mediaFileLengthInMilliseconds = mediaPlayer!!.duration

        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
            imgplaypause.setImageResource(R.drawable.white_play)
        } else {
            mediaPlayer!!.pause()
            imgplaypause.setClickable(true)
            imgplaypause.setImageResource(R.drawable.white_pause)
        }
        primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
    }


    private fun primarySeekBarProgressUpdater(fileLength: Int) {
        if (mediaPlayer!!.isPlaying) {
            val notification = Runnable {
                primarySeekBarProgressUpdater(fileLength)
                lblVoicetime.setText(milliSecondsToTimer(mediaPlayer!!.currentPosition.toLong()))
                playseekbar.setProgress(mediaPlayer!!.currentPosition)
                playseekbar.updatePlayerPercent(mediaPlayer!!.currentPosition.toFloat() / mediaPlayer!!.duration)
                primarySeekBarProgressUpdater(fileLength)

            }
            handler.postDelayed(notification, 1000)
        } else {
            lblVoicetime.setText("00:00")
            playseekbar.setProgress(0)
            playseekbar.updatePlayerPercent(0F)
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

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.imgplaypause -> {
                recplaypause()
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
            R.id.btnHomeWorkPublish -> {


                if (UtilConstants.MENU_TYPE == UtilConstants.MENU_TEXT_HOMEWORK) {
                    APIServices.sendHomeWork(this)
                } else if (UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE_HOMEWORK) {
                    UtilConstants.SelcetedFileList.add(VoiceFilePath!!)

                    awsFileUpload(this, 0)


                }
            }
        }
    }

}