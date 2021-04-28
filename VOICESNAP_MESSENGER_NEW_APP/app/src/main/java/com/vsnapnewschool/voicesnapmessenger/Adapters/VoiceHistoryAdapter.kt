package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.voiceHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import java.io.File
import java.util.*


class VoiceHistoryAdapter(private val imagelist: ArrayList<Text_Class>, private val context: Context?,val voiceHistoryListener: voiceHistoryListener) : RecyclerView.Adapter<VoiceHistoryAdapter.MyViewHolder>() {
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var mExpandedPosition:Int= -1
    var msgcontent: String? = null
    var mediaPlayer = MediaPlayer()
    var iMediaDuration:Int = 0

    companion object {
        var voicehisorylistener: voiceHistoryListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgplayvoice: ImageView
        val imgPlayPause: ImageView
        val imgsendvoice: ImageView
        val lblrectime: TextView
        val lblduration: TextView
        val rytVoice: RelativeLayout
        val rytVoicePlayer: RelativeLayout
        val seekbar: SeekBar

        init {
            imgplayvoice = view.findViewById<View>(R.id.imgplayvoice) as ImageView
            imgPlayPause = view.findViewById<View>(R.id.imgPlayPause) as ImageView
            imgsendvoice = view.findViewById<View>(R.id.imgsendvoice) as ImageView
            lblrectime = view.findViewById<View>(R.id.lblrectime) as TextView
            lblduration = view.findViewById<View>(R.id.lblduration) as TextView
            rytVoice = view.findViewById<View>(R.id.rytVoice) as RelativeLayout
            rytVoicePlayer = view.findViewById<View>(R.id.rytVoicePlayer) as RelativeLayout
            seekbar = view.findViewById<View>(R.id.seekbar) as SeekBar


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.voice_history, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val voice: Text_Class = imagelist.get(position)
        val isExpanded = position == mExpandedPosition
        holder.rytVoicePlayer.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
        holder.rytVoice.setActivated(isExpanded)

        voicehisorylistener = voiceHistoryListener
        voicehisorylistener?.voiceHistoryClick(holder,voice)

        holder.rytVoice.setOnClickListener(View.OnClickListener {
            msgcontent = voice.content
            mExpandedPosition = if (isExpanded) -1 else position
            notifyDataSetChanged()
            if (mediaPlayer != null && mediaPlayer.isPlaying) {
                Log.d("test",mediaPlayer.toString())
                mediaPlayer.stop()
                mediaPlayer.seekTo(0)
                holder.imgPlayPause.setImageResource(R.drawable.orange_pause)

            }
            mediaPlayer.setOnCompletionListener {
                mediaPlayer.seekTo(0)
                holder.imgPlayPause.setImageResource(R.drawable.orange_pause)

                Log.d("test_0",mediaPlayer.toString())

            }
            holder.seekbar.setMax(99) // It means 100% .0-99
            holder.seekbar.setOnTouchListener(object : OnTouchListener {
                override fun onTouch(v: View, event: MotionEvent): Boolean {
                    if (v.id == R.id.seekbar) {
                        run {
                            val sb = v as SeekBar
                            val playPositionInMillisecconds =
                                mediaFileLengthInMilliseconds / 100 * sb.progress
                            mediaPlayer.seekTo(playPositionInMillisecconds)
                        }
                    }
                    return false
                }
            })
            holder.seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (mediaPlayer != null && fromUser) {
                        val playPositionInMillisecconds: Int =
                            mediaFileLengthInMilliseconds / 100 * holder.seekbar.getProgress()
                        mediaPlayer.seekTo(playPositionInMillisecconds)
                    }
                }
            })
            Log.d("FetchSong", "Start***************************************")
            try {
                val filepath = msgcontent!!

                val dir = File(filepath)
                if (!dir.exists()) {
                    dir.mkdirs()
                    println("Dir: $dir")
                }
                val fileName = msgcontent!!.substring(msgcontent!!.lastIndexOf('/') + 1, msgcontent!!.length)
                println("FILE_PATH:$dir")
                mediaPlayer.reset()
                mediaPlayer.setDataSource(msgcontent)
                mediaPlayer.prepare()
                iMediaDuration = (mediaPlayer.duration / 1000.0).toInt()
            } catch (e: Exception) {
                Log.d("in Fetch Song", e.toString())
            }
            Log.d("FetchSong", "END***************************************")
        })

        holder.imgPlayPause.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                mediaFileLengthInMilliseconds = mediaPlayer.duration
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.start()
                    holder.imgPlayPause.setImageResource(R.drawable.orange_play)
                } else {
                    mediaPlayer.start()
                    holder.imgPlayPause.setImageResource(R.drawable.orange_pause)
                }
                primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds)
            }

            private fun primarySeekBarProgressUpdater(fileLength: Int) {
                val iProgress = (mediaPlayer.currentPosition.toFloat() / fileLength * 100).toInt()
                holder.seekbar.setProgress(iProgress)
                if (mediaPlayer.isPlaying) {
                    val notification = Runnable {
                        holder.imgPlayPause.setImageResource(R.drawable.orange_play)

                        holder.lblduration.setText(milliSecondsToTimer(mediaPlayer.currentPosition.toLong())
                        )
                        primarySeekBarProgressUpdater(fileLength)
                    }
                    handler.postDelayed(notification, 1000)
                }else{
                    holder.imgPlayPause.setImageResource(R.drawable.orange_pause)

                }
            }
        })
    }

    override fun getItemCount(): Int {
        return imagelist.size

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
