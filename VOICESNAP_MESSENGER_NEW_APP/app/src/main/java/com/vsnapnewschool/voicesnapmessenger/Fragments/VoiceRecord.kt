package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_EMERGENCY
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VOICE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MaxGeneralVoiceDuration
import kotlinx.android.synthetic.main.record_voice.*
import kotlinx.android.synthetic.main.voice_scroll_publish.*
import me.jagar.chatvoiceplayerlibrary.FileUtils
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream


@Suppress("DEPRECATION")
class VoiceRecord : Fragment(), OnClickListener {
    var mediaPlayer: MediaPlayer? = null
    var bIsRecording = false
    private val iRequestCode = 0
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var recTime = 0
    var recorder: MediaRecorder? = null
    var recTimerHandler = Handler()
    var iMediaDuration = 0
    var futureStudioIconFile: File? = null
    var iMaxRecDur = 180
    val VOICE_FOLDER_NAME = "NewSchool"
    val VOICE_FILE_NAME = "schoolVoice.mp3"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.voice_scroll_publish, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        btnschedule?.setOnClickListener(this)
        btnsend?.setOnClickListener(this)
        rytMonth?.setOnClickListener(this)
        rytTime?.setOnClickListener(this)
        btnNext?.setOnClickListener(this)
        imgRec?.setOnClickListener(this)
        imgPlayPasue?.setOnClickListener(this)
        rytEndMonth?.setOnClickListener(this)
        rytEndTime?.setOnClickListener(this)

        setupAudioPlayer()
    }

    private fun start_RECORD() {
        if(mediaPlayer!!.isPlaying){
            mediaPlayer!!.stop()
        }

        imgRec.setImageResource(R.drawable.voice_stop)
        imgRec.setClickable(true)
        imgPlayPasue.setVisibility(INVISIBLE)
        imgwave.setVisibility(GONE)

        if(MENU_TYPE== MENU_VOICE) {
            lnrVoiceButtons.visibility = View.GONE
        }

        try {
            recorder = MediaRecorder()
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder!!.setAudioEncodingBitRate(16)
            recorder!!.setAudioSamplingRate(44100)
            recorder!!.setOutputFile(getRecFilename())
            recorder!!.prepare()
            recorder!!.start()
            recTimeUpdation()
            bIsRecording = true
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun stop_RECORD() {
        recorder!!.stop()
        recTimerHandler.removeCallbacks(runson)
        bIsRecording = false
        imgRec.setImageResource(R.drawable.voice_bg)
        imgPlayPasue.setVisibility(VISIBLE)

        if(MENU_TYPE== MENU_VOICE){
            lnrVoiceButtons.visibility=View.VISIBLE

        }

        fetchSong()
        imgwave.setVisibility(VISIBLE)
        imgwave.getProgressDrawable().setColorFilter(context!!.getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN)
        imgwave.getThumb().setColorFilter(context!!.getResources().getColor(android.R.color.transparent), PorterDuff.Mode.SRC_IN)
        imgwave.setMax(mediaPlayer!!.getDuration())
        imgwave.setProgress(0)
        imgwave.updatePlayerPercent(0F)

        imgwave.updateVisualizer(FileUtils.fileToBytes(File(futureStudioIconFile!!.path)))
        imgwave.setColors(Color.parseColor("#fb6b22"), Color.parseColor("#7C7C7C"))
    }

    private fun setupAudioPlayer() {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setOnCompletionListener {
            imgPlayPasue.setImageResource(R.drawable.orange_pause)
            mediaPlayer!!.seekTo(0)
        }
    }

    fun recplaypause() {
        mediaFileLengthInMilliseconds = mediaPlayer!!.duration

        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
            imgPlayPasue.setImageResource(R.drawable.orange_play)

//            btnsend.setBackground(resources.getDrawable(R.drawable.bg_corners_circle_orange))
//            btnsend.setTextColor(Color.parseColor("#FFFFFFFF"))

        } else {
            mediaPlayer!!.pause()
            imgPlayPasue.setClickable(true)
            imgPlayPasue.setImageResource(R.drawable.orange_pause)
        }
        primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
    }
    private fun primarySeekBarProgressUpdater(fileLength: Int) {
        if (mediaPlayer!!.isPlaying) {
            val notification = Runnable {
                primarySeekBarProgressUpdater(fileLength)
                imgwave.setProgress(mediaPlayer!!.currentPosition)
                imgwave.updatePlayerPercent(mediaPlayer!!.currentPosition.toFloat() / mediaPlayer!!.duration)
            }
            handler.postDelayed(notification, 1000)
        }
        else{
            imgwave.setProgress(0)
            imgwave.updatePlayerPercent(0F)
        }
    }

    fun getRecFilename(): String? {
        var filepath: String
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filepath=activity!!.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()

        } else {
            filepath = Environment.getExternalStorageDirectory().getPath()
        }

        val fileDir = File(filepath,VOICE_FOLDER_NAME)
        if (!fileDir.exists()) {
            fileDir.mkdirs()
        }
        val fileNamePath = File(fileDir, VOICE_FILE_NAME)
        return fileNamePath.path



    }

    fun recTimeUpdation() {
        recTime = 1
        recTimerHandler.postDelayed(runson, 1000)
    }

    val runson: Runnable = object : Runnable {
        override fun run() {
            lbltime.setText(milliSecondsToTimer(recTime * 1000.toLong()))
            val timing: String = lbltime.getText().toString()
            if (lbltime.getText().toString() != "00:00") {
                imgRec.setEnabled(true)
            }
            recTime = recTime + 1
            if (recTime != MaxGeneralVoiceDuration) {
                recTimerHandler.postDelayed(this, 1000)
            } else {
                stop_RECORD()
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

    fun fetchSong() {
        btnNext.isEnabled=true
        Log.d("FetchSong", "Start***************************************")
        try {
            val filepath:String
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filepath=activity!!.getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()
            } else {
                filepath = Environment.getExternalStorageDirectory().getPath()
            }

            val fileDir = File(filepath,VOICE_FOLDER_NAME)

            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }

            futureStudioIconFile = File(fileDir, VOICE_FILE_NAME)
            UtilConstants.VoiceFilePath=futureStudioIconFile?.path
            println("FILE_PATH:" + futureStudioIconFile!!.path)
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(futureStudioIconFile!!.path)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()
            UtilConstants.VoiceDuration=iMediaDuration.toString()

        } catch (e: Exception) {
            Log.d("in Fetch Song", e.toString())
        }
        Log.d("FetchSong", "END***************************************")
    }

    fun backToResultActvity(msg: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("MESSAGE", msg)
        activity!!.setResult(iRequestCode, returnIntent)
        activity!!.finish()
    }
    fun onBackPressed(): Boolean {
        if (mediaPlayer!!.isPlaying) mediaPlayer!!.stop()
        if (bIsRecording) stop_RECORD()
        backToResultActvity("SENT")
        return true
    }

    override fun onResume() {
        super.onResume()
        activity!!.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer!!.isPlaying) {
            imgRec.setClickable(false)
            mediaPlayer!!.pause()
            imgRec.setImageResource(R.drawable.voice_play)
        }
        if (bIsRecording) imgRec.setClickable(true)
        activity!!.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    @SuppressLint("ResourceAsColor")
    override fun onClick(v: View?) {
        when(v!!.id) {

            R.id.btnschedule -> {
                UtilConstants.ScheduleType="schedule"
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                constScheduleDate.visibility=VISIBLE
                btnschedule.setBackground(resources.getDrawable(R.drawable.bg_corners_circle_orange))
                btnsend.setTextColor(R.color.clr_school)
                btnsend.setBackgroundResource(R.drawable.circle_white_orange_send)
                btnschedule.setTextColor(Color.parseColor("#ffffff"))

            }
            R.id.btnsend -> {
                UtilConstants.ScheduleType="instant"
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                constScheduleDate.visibility=GONE
                btnsend.setBackground(resources.getDrawable(R.drawable.bg_corners_circle_orange))
                btnschedule.setTextColor(R.color.clr_school)
                btnschedule.setBackgroundResource(R.drawable.bg_corners_circle_white)
                btnsend.setTextColor(Color.parseColor("#ffffff"))

            }
            R.id.rytMonth -> {
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                activity?.let { it1 -> (activity as BaseActivity?)!!.PickDate(it1,lblDate,true,0) }
            }
            R.id.rytTime -> {
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                activity?.let { it1 -> (activity as BaseActivity?)!!.PickTime(it1, lblSelectTime,0) }

            }
            R.id.rytEndMonth ->{
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                activity?.let { it1 -> (activity as BaseActivity?)!!.PickDate(it1,lblEndDate,true,1) }
            }
            R.id.rytEndTime ->{
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                activity?.let { it1 -> (activity as BaseActivity?)!!.PickTime(it1, lblSelectEndTime,1) }
            }
            R.id.btnNext -> {
                UtilConstants.Title=edTitle.text.toString()
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)

                if(MENU_TYPE==MENU_EMERGENCY){
                    UtilConstants.schoolListScreen(context as Activity?)
                }
                else{
                    UtilConstants.recipientsActivity(activity!!)
                }

            }

            R.id.imgRec -> {
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                if (bIsRecording) {
                    if (lbltime.getText().toString() == "00:00") {
                        imgRec.setClickable(false)
                        imgRec.setEnabled(false)
                    } else {
                        stop_RECORD()
                    }
                } else {
                    start_RECORD()
                }
            }
            R.id.imgPlayPasue -> {
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)
                recplaypause()

            }

        }
    }
}