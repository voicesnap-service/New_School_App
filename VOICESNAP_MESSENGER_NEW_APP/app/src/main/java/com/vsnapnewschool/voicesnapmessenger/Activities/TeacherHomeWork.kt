package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.WindowManager

import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MaxHomeWorkSMSCount
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.homework_publish.*
import kotlinx.android.synthetic.main.homework_publish.edDescription
import kotlinx.android.synthetic.main.homework_publish.imgPlayPasue
import kotlinx.android.synthetic.main.homework_publish.imgRec
import kotlinx.android.synthetic.main.homework_publish.lblRemaining
import kotlinx.android.synthetic.main.homework_scroll.btnHomeworkNext
import kotlinx.android.synthetic.main.record_voice.*
import me.jagar.chatvoiceplayerlibrary.FileUtils
import java.io.File
import java.io.IOException


class TeacherHomeWork : BaseActivity(), View.OnClickListener {
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homework_scroll)
        enableCrashLytics()
        initializeActionBar()
        setTitle(getString(R.string.title_Homework))
        enableSearch(false)

        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        btnHomeworkNext?.setOnClickListener(this)
        imgRec?.setOnClickListener(this)
        imgPlayPasue?.setOnClickListener(this)
        setEditTextMaxLength(edDescription, MaxHomeWorkSMSCount!!)

        EditTextWatcher(btnHomeworkNext, edDescription)



        if (UtilConstants.MENU_TYPE == UtilConstants.MENU_TEXT_HOMEWORK) {
            LayoutTextHomework.visibility = View.VISIBLE
        } else if (UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE_HOMEWORK) {
            LayoutHomeworkVoice.visibility = View.VISIBLE
        }

     //   edDescription.maxEms= MaxHomeWorkSMSCount!!
        lblRemaining.text=MaxHomeWorkSMSCount?.toString()+" "+getString(R.string.lbl_remaining)
        edDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > 0 && edHomeWorkTitle.text.length > 0) {
                    lblRemaining.setText("" + (UtilConstants.MaxHomeWorkSMSCount!! - s.length))

                    btnHomeworkNext.setEnabled(true)
                } else {
                    btnHomeworkNext.setEnabled(false)
                }

            }

        })

        setupAudioPlayer()

    }

    private fun start_RECORD() {
        if (mediaPlayer!!.isPlaying) {
            mediaPlayer!!.stop()
        }

        imgRec.setImageResource(R.drawable.voice_stop)
        imgRec.setClickable(true)
        imgPlayPasue.setVisibility(View.INVISIBLE)
        imgHomeworkWave.setVisibility(View.GONE)

        if (UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE) {
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
        imgPlayPasue.setVisibility(View.VISIBLE)

        if (UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE) {
            lnrVoiceButtons.visibility = View.VISIBLE

        }

        fetchSong()
        imgHomeworkWave.setVisibility(View.VISIBLE)
        imgHomeworkWave.getProgressDrawable().setColorFilter(
            getResources().getColor(android.R.color.transparent),
            PorterDuff.Mode.SRC_IN
        )
        imgHomeworkWave.getThumb().setColorFilter(
            getResources().getColor(android.R.color.transparent),
            PorterDuff.Mode.SRC_IN
        )
        imgHomeworkWave.setMax(mediaPlayer!!.getDuration())
        imgHomeworkWave.setProgress(0)
        imgHomeworkWave.updatePlayerPercent(0F)
        imgHomeworkWave.updateVisualizer(FileUtils.fileToBytes(File(futureStudioIconFile!!.path)))
        imgHomeworkWave.setColors(Color.parseColor("#fb6b22"), Color.parseColor("#7C7C7C"))
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
                imgHomeworkWave.setProgress(mediaPlayer!!.currentPosition)
                imgHomeworkWave.updatePlayerPercent(mediaPlayer!!.currentPosition.toFloat() / mediaPlayer!!.duration)
            }
            handler.postDelayed(notification, 1000)
        } else {
            imgHomeworkWave.setProgress(0)
            imgHomeworkWave.updatePlayerPercent(0F)
        }
    }

    fun getRecFilename(): String? {
        val filepath: String
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            filepath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .getPath()
        } else {
            filepath = Environment.getExternalStorageDirectory().getPath();
        }
        val fileDir = File(filepath, VOICE_FOLDER_NAME)
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
            lblVoicetime.setText(milliSecondsToTimer(recTime * 1000.toLong()))
            val timing: String = lblVoicetime.getText().toString()
            if (lblVoicetime.getText().toString() != "00:00") {
                imgRec.setEnabled(true)
            }
            recTime = recTime + 1
            if (recTime != iMaxRecDur) recTimerHandler.postDelayed(this, 1000)
            else stop_RECORD()
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
        btnHomeworkNext.isEnabled = true
        Log.d("FetchSong", "Start***************************************")
        try {
            val filepath: String
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filepath =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .getPath()

            } else {
                filepath = Environment.getExternalStorageDirectory().getPath();
            }

            val fileDir = File(filepath, VOICE_FOLDER_NAME)

            if (!fileDir.exists()) {
                fileDir.mkdirs()
            }

            futureStudioIconFile = File(fileDir, VOICE_FILE_NAME)
            UtilConstants.VoiceFilePath = futureStudioIconFile?.path
            println("FILE_PATH:" + futureStudioIconFile!!.path)
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(futureStudioIconFile!!.path)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()
            UtilConstants.VoiceDuration = iMediaDuration.toString()

        } catch (e: Exception) {
            Log.d("in Fetch Song", e.toString())
        }
        Log.d("FetchSong", "END***************************************")
    }

    fun backToResultActvity(msg: String) {
        val returnIntent = Intent()
        returnIntent.putExtra("MESSAGE", msg)
        this.setResult(iRequestCode, returnIntent)
        this.finish()
    }

    override fun onBackPressed() {
        if (mediaPlayer!!.isPlaying) mediaPlayer!!.stop()
        if (bIsRecording) stop_RECORD()
        backToResultActvity("SENT")
    }

    override fun onResume() {
        super.onResume()
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onPause() {
        super.onPause()
        if (mediaPlayer!!.isPlaying) {
            imgRec.setClickable(false)
            mediaPlayer!!.pause()
            imgRec.setImageResource(R.drawable.voice_play)
        }
        if (bIsRecording) imgRec.setClickable(true)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.btnHomeworkNext -> {
                HideKeyboard_Fragment(this)
                if (UtilConstants.MENU_TYPE == UtilConstants.MENU_TEXT_HOMEWORK) {
                    UtilConstants.Title = edHomeWorkTitle.text.toString()
                } else if (UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE_HOMEWORK) {
                    UtilConstants.Title = edVoiceTitle.text.toString()
                }
                UtilConstants.Description = edDescription.text.toString()
                UtilConstants.RecipientsType = UtilConstants.StandardSection
                UtilConstants.SpecificSectionsScreen(this)

            }
            R.id.imgRec -> {
                HideKeyboard_Fragment(this)
                if (bIsRecording) {
                    if (lblVoicetime.getText().toString() == "00:00") {
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
                HideKeyboard_Fragment(this)
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
        }


}


}
