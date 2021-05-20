package com.vsnapnewschool.voicesnapmessenger.Activities

import android.app.Activity
import android.graphics.Color
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentVoiceAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.VoiceMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetHomeWorkListResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.Utils.DownloadVoice
import com.vsnapnewschool.voicesnapmessenger.Utils.VoiceHistoryDownload
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.homework_history.*
import kotlinx.android.synthetic.main.homework_history.LayoutVoicePlay
import kotlinx.android.synthetic.main.homework_history.lblSentAt
import kotlinx.android.synthetic.main.homework_history.playseekbar
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import me.jagar.chatvoiceplayerlibrary.FileUtils
import java.io.File


class ParentHomeWorkViewScreen : BaseActivity(),View.OnClickListener {
    lateinit var Content: GetHomeWorkListResponse.ParentHomeworklist
    var iMediaDuration = 0
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var iMaxRecDur = 180
    var recTime = 0
    var recTimerHandler = Handler()
    var mediaPlayer:MediaPlayer= MediaPlayer()
    var path: String = ""
    private val VOICE_FOLDER: String? = "NewSchool/Voice/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homework_history)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Homework))
        enableSearch(false)
        scrollAdds(this,imageSlider)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        imgplaypause?.setOnClickListener(this)
        val type:String = intent.getStringExtra("type")!!
        Content =
            intent.getSerializableExtra("content") as GetHomeWorkListResponse.ParentHomeworklist
        if(Content.homework_topic.equals("")){
            lblHomework.visibility=View.GONE
            homework.visibility=View.GONE
        }else{
            lblHomework.visibility=View.VISIBLE
            homework.visibility=View.VISIBLE
        }

        if(Content.homework_voice.equals("")){
            LayoutVoicePlay.visibility=View.GONE
        } else{
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .path + File.separator + VOICE_FOLDER+ Content.day_id +".mp3"
            }
            else{
                path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + VOICE_FOLDER+ Content.day_id +".mp3"
            }
            val filename: String =Content.day_id+".mp3"
            val savefile: File = File(path)

            if (!savefile.exists()) {
                LayoutVoicePlay.visibility=View.GONE
//                VoiceHistoryDownload.downloadHistoryFile(
//                    this,
//                    Content.homework_voice,
//                    VOICE_FOLDER,
//                    filename,
//                    "HomeWork"
//                )
                DownloadVoice.downloadSampleFile(this, Content.homework_voice, VOICE_FOLDER, filename, object:VoiceMessagesClickListener{
                    override fun onVoiceClick(
                        holder: ParentVoiceAdapter.MyViewHolder,
                        item: GetVoiceData
                    ) {

                    }

                    override fun onrefresh() {
                        refreshvoice()
                    }

                })


            }
            else {
                LayoutVoicePlay.visibility=View.VISIBLE
                UtilConstants.VoiceFilePath = path
                Log.d("voicefilepathelse",UtilConstants.VoiceFilePath.toString())
                fetchSong()
                setupAudioPlayer()

            }


        }
        if(type.equals("0")){
            val window: Window = this.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.clr_parent)
        }
        lblSentAt.text= Content.created_on
        lblSubject.text= Content.subject_name
        lblTeacherName.text= Content.created_by
        lblhomeworktitle.text= Content.homework_text
        lblHomework.text= Content.homework_topic



//        imgplaypause
//        lblVoicetime
//        playseekbar
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
            R.id.imgplaypause -> {
                recplaypause()
            }

        }
    }

    fun fetchSong() {

        try {

            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(UtilConstants.VoiceFilePath)
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
        mediaPlayer!!.setOnCompletionListener {
            imgplaypause.setImageResource(R.drawable.white_pause)
            mediaPlayer!!.seekTo(0)
//            playseekbar.setProgress(0)
//            playseekbar.updatePlayerPercent(0F)
        }

        playseekbar.setOnSeekBarChangeListener(seekBarListener);

    }

    fun recplaypause() {

        if (!mediaPlayer!!.isPlaying) {
            mediaPlayer!!.start()
            imgplaypause.setImageResource(R.drawable.white_play)
        } else {
            mediaPlayer!!.pause()
            imgplaypause.setClickable(true)
            imgplaypause.setImageResource(R.drawable.white_pause)
        }
        mediaFileLengthInMilliseconds = mediaPlayer!!.duration

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

    val runson: Runnable = object : Runnable {
        override fun run() {
            lblVoicetime.setText(milliSecondsToTimer(recTime * 1000.toLong()))
            val timing: String = lblVoicetime.getText().toString()
            if (lblVoicetime.getText().toString() != "00:00") {
                imgplaypause.setEnabled(true)
            }
            recTime = recTime + 1
            if (recTime != iMaxRecDur)
                recTimerHandler.postDelayed(this, 1000)
        }
    }

    fun update(mediaPlayer: MediaPlayer, seekBar: SeekBar) {
        (this as Activity).runOnUiThread(Runnable {
            seekBar.progress = mediaPlayer.currentPosition
            if (mediaPlayer.isPlaying) {
                playseekbar.setProgress(mediaPlayer.currentPosition)
                playseekbar.updatePlayerPercent(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration)
            }
            else {

                seekBar.progress = 0
                playseekbar.updatePlayerPercent(0F)
                playseekbar.setProgress(0)
            }
            val handler = Handler()
            try {
                val runnable = Runnable {
                    try {
                        if (mediaPlayer.currentPosition > -1) {
                            try {
                                update(mediaPlayer, seekBar)
                            } catch (e: java.lang.Exception) {
                                e.printStackTrace()
                            }
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
                handler.postDelayed(runnable, 2)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        })
    }

    val seekBarListener: SeekBar.OnSeekBarChangeListener = object :
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            if (fromUser) {
                mediaPlayer.seekTo(progress)
                update(mediaPlayer, seekBar)
                if (playseekbar.getVisibility() == View.VISIBLE) {
                    playseekbar.updatePlayerPercent(
                        mediaPlayer.currentPosition
                            .toFloat() / mediaPlayer.duration
                    )
                }
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            imgplaypause.setImageResource(R.drawable.white_pause)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            imgplaypause.setImageResource(R.drawable.white_play)
            mediaPlayer.start()
        }
    }

    override fun onResume() {
        super.onResume()

    }

    fun refreshvoice() {
        this.recreate()
    }

}