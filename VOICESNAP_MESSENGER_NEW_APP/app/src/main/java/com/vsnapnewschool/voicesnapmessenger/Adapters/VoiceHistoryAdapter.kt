package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.media.MediaPlayer
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.voiceHistoryListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.VoiceHistoryData
import kotlinx.android.synthetic.main.activity_final_preview_voice.*
import java.io.File
import java.util.*


class VoiceHistoryAdapter(
    private val voiceHistoryList: ArrayList<VoiceHistoryData>,
    val context: Context,
    val voiceHistoryListener: voiceHistoryListener
) : RecyclerView.Adapter<VoiceHistoryAdapter.MyViewHolder>() {
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var mExpandedPosition:Int= -1
    var msgcontent: String? = null
    var mediaPlayer: MediaPlayer? = null
    var futureStudioIconFile: File? = null

    var iMediaDuration:Int = 0
    var path:String? = null
    var filename:String? = null
    val VOICE_FOLDER_NAME = "NewSchool"
    val VOICE_FILE_NAME = "schoolVoice.mp3"
    private val VOICE_FOLDER: String? = "School Voice/Voice"

    companion object {
        var voicehisorylistener: voiceHistoryListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgplayvoice: ImageView
        val imgPlayPause: ImageView
        val imgsendvoice: ImageView
        val lblVoiceDuration: TextView
        val lblduration: TextView
        val lblsentTime: TextView
        val lblTotalVoiceTime: TextView
        val lbltitle: TextView
        val rytVoice: RelativeLayout
        val rytVoicePlayer: RelativeLayout
        val lnrExpandVoice: LinearLayout
        val seekbar: SeekBar

        init {
            imgplayvoice = view.findViewById<View>(R.id.imgplayvoice) as ImageView
            imgPlayPause = view.findViewById<View>(R.id.imgPlayPause) as ImageView
            imgsendvoice = view.findViewById<View>(R.id.imgsendvoice) as ImageView
            lblVoiceDuration = view.findViewById<View>(R.id.lblVoiceDuration) as TextView
            lblduration = view.findViewById<View>(R.id.lblduration) as TextView
            lblsentTime = view.findViewById<View>(R.id.lblsentTime) as TextView
            lbltitle = view.findViewById<View>(R.id.lbltitle) as TextView
            lblTotalVoiceTime = view.findViewById<View>(R.id.lblTotalVoiceTime) as TextView
            rytVoice = view.findViewById<View>(R.id.rytVoice) as RelativeLayout
            rytVoicePlayer = view.findViewById<View>(R.id.rytVoicePlayer) as RelativeLayout
            lnrExpandVoice = view.findViewById<View>(R.id.lnrExpandVoice) as LinearLayout
            seekbar = view.findViewById<View>(R.id.seekbar) as SeekBar


        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.voice_history, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val voiceData: VoiceHistoryData = voiceHistoryList.get(position)

        val isExpanded = position == mExpandedPosition
        holder.rytVoicePlayer.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.lnrExpandVoice.setActivated(isExpanded)

        voicehisorylistener = voiceHistoryListener
        voicehisorylistener?.voiceHistoryClick(holder, voiceData)
        holder.lblsentTime.text=voiceData.created_on
        holder.lbltitle.text=voiceData.description
        val voiceduration: Int = voiceData.duration.toInt()
        val hours = (voiceduration / 3600).toString()
        val minutes = voiceduration % 3600 / 60
        val seconds = voiceduration % 60
        val timeString = String.format("%02d:%02d", minutes, seconds)
        holder.lblVoiceDuration.setText(timeString)
        filename=voiceData.sub_or_file_name

        holder.rytVoice.setOnClickListener(View.OnClickListener {
            msgcontent = voiceData.voice_file_path
            mExpandedPosition = if (isExpanded) -1 else position

            notifyDataSetChanged()
            holder.lblTotalVoiceTime.text = timeString


//            VoiceHistoryDownload.downloadHistoryFile(
//                this.context,
//                voiceData.voice_file,
//                VOICE_FOLDER,
//                fileName
//            )
            mediaPlayer = MediaPlayer()

            fetchSong()


            mediaPlayer!!.setOnCompletionListener {
                mediaPlayer!!.seekTo(0)
                holder.imgPlayPause.setImageResource(R.drawable.orange_pause)

            }

            holder.seekbar.setMax(99) // It means 100% .0-99
            holder.seekbar.setOnTouchListener(object : OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    if (v.id == R.id.seekbar) {
                        run {
                            val sb = v as SeekBar
                            val playPositionInMillisecconds =
                                mediaFileLengthInMilliseconds / 100 * sb.progress
                            mediaPlayer!!.seekTo(playPositionInMillisecconds)
                        }
                    }
                    return false
                }
            })
            holder.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (mediaPlayer!! != null && fromUser) {
                        val playPositionInMillisecconds: Int =
                            mediaFileLengthInMilliseconds / 100 * holder.seekbar.getProgress()
                        mediaPlayer!!.seekTo(playPositionInMillisecconds)
                    }
                }
            })
            Log.d("FetchSong", "Start***************************************")

        })

        holder.imgPlayPause.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {

                mediaFileLengthInMilliseconds = mediaPlayer!!.duration
                if (!mediaPlayer!!.isPlaying) {
                    mediaPlayer!!.start()
                    holder.imgPlayPause.setImageResource(R.drawable.orange_play)
                } else {
                    mediaPlayer!!.start()
                    //     mediaPlayer.pause()
                    holder.imgPlayPause.setImageResource(R.drawable.orange_pause)
                }
                primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
            }

            private fun primarySeekBarProgressUpdater(fileLength: Int) {
                val iProgress = (mediaPlayer!!.currentPosition.toFloat() / fileLength * 100).toInt()
                holder.seekbar.setProgress(iProgress)
                if (mediaPlayer!!.isPlaying) {
                    val notification = Runnable {
                        holder.imgPlayPause.setImageResource(R.drawable.orange_play)

                        holder.lblduration.setText(
                            milliSecondsToTimer(mediaPlayer!!.currentPosition.toLong())
                        )
                        primarySeekBarProgressUpdater(fileLength)
                    }
                    handler.postDelayed(notification, 1000)
                } else {
                    holder.imgPlayPause.setImageResource(R.drawable.orange_pause)

                }
            }
        })
    }

    override fun getItemCount(): Int {
        return voiceHistoryList.size

    }


    fun fetchSong() {

        try {
            val filepath: String
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                filepath=context.applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)!!.getPath()

            } else {

                filepath = Environment.getExternalStorageDirectory().getPath();
            }


            val file = File(filepath, VOICE_FOLDER)
            val fileDir = File(file.absolutePath)

            if (!fileDir.exists()) {
                fileDir.mkdirs()
                println("Dir: $fileDir")
            }

            futureStudioIconFile = File(fileDir, VOICE_FILE_NAME)
            mediaPlayer!!.reset()
            mediaPlayer!!.setDataSource(futureStudioIconFile!!.path)
            mediaPlayer!!.prepare()
            iMediaDuration = (mediaPlayer!!.duration / 1000.0).toInt()


        } catch (e: Exception) {
            Log.d("in Fetch Song", e.toString())
        }
        Log.d("FetchSong", "END***************************************")
    }




    fun milliSecondsToTimer(milliseconds: Long): String? {
        var finalTimerString = ""
        var secondsString = ""
        var minutesString = ""

        // Convert total duration into time
        val hours = (milliseconds / (1000 * 60 * 60)).toInt()
        val minutes = (milliseconds % (1000 * 60 * 60)).toInt() / (1000 * 60)
        val seconds = (milliseconds % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        // Add hours if there
        if (hours > 0) {
            finalTimerString = "$hours:"
        }

        // Prepending 0 to Minutes if it is one digit
        minutesString = if (minutes < 10) {
            "0$minutes"
        } else {
            "" + minutes
        }

        // Prepending 0 to seconds if it is one digit
        secondsString = if (seconds < 10) {
            "0$seconds"
        } else {
            "" + seconds
        }
        finalTimerString = "$finalTimerString$minutesString:$secondsString"

        // return timer string
        return finalTimerString
    }

}
