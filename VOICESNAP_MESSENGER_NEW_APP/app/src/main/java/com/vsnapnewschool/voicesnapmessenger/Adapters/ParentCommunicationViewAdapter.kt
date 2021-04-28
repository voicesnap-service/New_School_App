package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.PorterDuff
import android.media.MediaPlayer
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.Refreshlistener
import com.vsnapnewschool.voicesnapmessenger.Models.Voice_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.Utils.DownloadVoice
import me.jagar.chatvoiceplayerlibrary.FileUtils
import me.jagar.chatvoiceplayerlibrary.PlayerVisualizerSeekbar
import java.io.File
import java.util.*


class ParentCommunicationViewAdapter(private val imagelist: ArrayList<Voice_Class>, private val context: Context?,var mediaPlayer: MediaPlayer,val refreshlistener: Refreshlistener) :
    RecyclerView.Adapter<ParentCommunicationViewAdapter.MyViewHolder>(){
    var mediaFileLengthInMilliseconds = 0
    var pauselength = 0
    var handler = Handler()
    var path: String? = null
    var prevposition = -1
    var selectposition = -1
    private val VOICE_FOLDER: String? = "School Voice/Voice"
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        internal var lblcountvoice: TextView
        internal var lblunread: TextView
        internal var lbltime: TextView
        internal var lblvoicetime: TextView
        internal var imgplay: ImageView
        internal var imgdownload: ImageView
        internal var imgwave: PlayerVisualizerSeekbar
        internal var rlvoice: RelativeLayout

        init {
            lblcountvoice = view.findViewById<View>(R.id.lblcountvoice) as TextView
            lblunread = view.findViewById<View>(R.id.lblunread) as TextView
            lbltime = view.findViewById<View>(R.id.lbltime) as TextView
            lblvoicetime = view.findViewById<View>(R.id.lblvoicetime) as TextView
            lbltime = view.findViewById<View>(R.id.lbltime) as TextView
            imgplay = view.findViewById<View>(R.id.imgplay) as ImageView
            imgdownload = view.findViewById<View>(R.id.imgdownload) as ImageView
            imgwave = view.findViewById<View>(R.id.imgwave) as PlayerVisualizerSeekbar
            rlvoice = view.findViewById<View>(R.id.rlvoice) as RelativeLayout
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun bind(menuModel: Voice_Class) {
            imgplay.setOnClickListener {
//              menuModel.selectstatus=0 start
//              menuModel.selectstatus=1 play
//              menuModel.selectstatus=-1 pause
                prevposition = selectposition
                selectposition = menuModel.id!!
                if(prevposition!=-1){
                    if(prevposition!=selectposition) {
                        menuModel.selectstatus = 0
                        menuModel.pause = -1
                    }
                }
                if (menuModel.selectstatus == 0) {
                    menuModel.selectstatus = 1
                } else if(menuModel.selectstatus==1) {
                    if(mediaPlayer.isPlaying) {
                        menuModel.selectstatus = -1
                    }

                    else{
                        menuModel.selectstatus = 1
                    }
                    mediaPlayer.setOnCompletionListener {
                        imgplay.setImageResource(R.drawable.white_pause)
                        mediaPlayer.seekTo(0)
                        lblvoicetime.setText("00:00")
                        imgwave.setProgress(0)
                        imgwave.updatePlayerPercent(0F)
                        menuModel.selectstatus=0

                    }

                }
                notifyItemRangeChanged(0,imagelist.size)

            }
        }
        fun primarySeekBarProgressUpdater(fileLength: Int,id:Int,pas:Int) {

            if(selectposition==id) {
                if (mediaPlayer.isPlaying) {
                    val notification = Runnable {
                        lblvoicetime.setText(milliSecondsToTimer(mediaPlayer.currentPosition.toLong()))
                        imgwave.setProgress(mediaPlayer.currentPosition)
                        imgwave.updatePlayerPercent(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration)
                        primarySeekBarProgressUpdater(fileLength, id,pas)
                    }
                    handler.postDelayed(notification, 1000)
                }
                else if (pas == selectposition) {
                    val notification = Runnable {
                        lblvoicetime.setText(milliSecondsToTimer(mediaPlayer.currentPosition.toLong()))
                        imgwave.setProgress(mediaPlayer.currentPosition)
                        imgwave.updatePlayerPercent(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration)
                        primarySeekBarProgressUpdater(fileLength, id, pas)
                    }
                    handler.postDelayed(notification, 1000)
                }
                else{
                    lblvoicetime.setText("00:00")
                    imgwave.setProgress(0)
                    imgwave.updatePlayerPercent(0F)
                }

            }
            else{
                lblvoicetime.setText("00:00")
                imgwave.setProgress(0)
                imgwave.updatePlayerPercent(0F)
            }

        }
        fun update(mediaPlayer: MediaPlayer, seekBar: SeekBar) {
            (context as Activity).runOnUiThread(Runnable {
                seekBar.progress = mediaPlayer.currentPosition
                if (mediaPlayer.isPlaying) {
                    imgwave.setProgress(mediaPlayer.currentPosition)
                    imgwave.updatePlayerPercent(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration)
                }
                else {

                    seekBar.progress = 0
                    imgwave.updatePlayerPercent(0F)
                    imgwave.setProgress(0)
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
                    if (imgwave.getVisibility() == VISIBLE) {
                        imgwave.updatePlayerPercent(
                            mediaPlayer.currentPosition
                                .toFloat() / mediaPlayer.duration
                        )
                    }
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                imgplay.setImageResource(R.drawable.white_pause)
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                imgplay.setImageResource(R.drawable.white_play)
                mediaPlayer.start()
            }
        }
        @RequiresApi(Build.VERSION_CODES.M)
        fun changeToSelect(resid: Int,menuModel:Voice_Class) {
            imgplay.setImageResource(resid)


            imgwave.setVisibility(VISIBLE)
            lblvoicetime.setText("00:00")
            imgwave.getProgressDrawable().setColorFilter(
                context!!.getResources().getColor(android.R.color.transparent),
                PorterDuff.Mode.SRC_IN
            )
            imgwave.getThumb().setColorFilter(
                context.getResources().getColor(android.R.color.transparent),
                PorterDuff.Mode.SRC_IN
            )
            imgwave.setMax(mediaPlayer.getDuration())
            imgwave.setProgress(0)
            imgwave.updatePlayerPercent(0F)

            imgwave.updateVisualizer(FileUtils.fileToBytes(File(path)))
            imgwave.setColors(
                context.getColor(R.color.white),
                context.getColor(R.color.black)
            )
            if (selectposition == menuModel.id) {
                Log.d("selposchsel",menuModel.selectstatus.toString())
                Log.d("selposchpas",menuModel.pause.toString())
                Log.d("selposchid",selectposition.toString())
                if(menuModel.pause==selectposition && menuModel.selectstatus==1 ){
                    mediaPlayer.seekTo(pauselength)
                    mediaPlayer.start()
                    mediaFileLengthInMilliseconds = mediaPlayer.duration
                    primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds, menuModel.id!!,
                        menuModel.pause!!
                    )
                    imgwave.setVisibility(VISIBLE)
                    imgwave.getProgressDrawable().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.getThumb().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.setMax(mediaPlayer.getDuration())
                    imgwave.updateVisualizer(FileUtils.fileToBytes(File(path)))
                    imgwave.setColors(
                        context.getColor(R.color.white),
                        context.getColor(R.color.black)
                    )
                }
                else if (menuModel.selectstatus == 1) {
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.stop()
                    }
                    mediaPlayer.reset()
                    mediaPlayer.setDataSource(path)
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                    mediaFileLengthInMilliseconds = mediaPlayer.duration
                    primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds, menuModel.id!!,
                        menuModel.pause!!)
                    imgwave.setVisibility(VISIBLE)
                    imgwave.getProgressDrawable().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.getThumb().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.setMax(mediaPlayer.getDuration())
                    imgwave.updatePlayerPercent(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration)
                    imgwave.updateVisualizer(FileUtils.fileToBytes(File(path)))
                    imgwave.setColors(
                        context.getColor(R.color.white),
                        context.getColor(R.color.black)
                    )

                }

                else if(menuModel.selectstatus==-1){
                    mediaPlayer.pause()
                    mediaFileLengthInMilliseconds = mediaPlayer.duration
                    pauselength=mediaPlayer.currentPosition

                    menuModel.pause=menuModel.id
                    menuModel.selectstatus=1
                    primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds, menuModel.id!!,
                        menuModel.pause!!)
                    imgwave.setVisibility(VISIBLE)
                    imgwave.getProgressDrawable().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.getThumb().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.setProgress(pauselength)
                    imgwave.setMax(mediaPlayer.getDuration())
                    imgwave.updateVisualizer(FileUtils.fileToBytes(File(path)))
                    imgwave.setColors(
                        context.getColor(R.color.white),
                        context.getColor(R.color.black)
                    )
                }
                imgwave.setOnSeekBarChangeListener(seekBarListener);
                mediaPlayer.setOnCompletionListener {
                    imgplay.setImageResource(R.drawable.white_pause)
                    mediaPlayer.seekTo(0)
                    lblvoicetime.setText("00:00")
                    imgwave.setProgress(0)
                    imgwave.updatePlayerPercent(0F)
                }
            }

        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_communiacation_messages, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val info: Voice_Class = imagelist[position]
        holder.lblunread.setVisibility(GONE)
        holder.lbltime.setText(info.time)




        if (info.status.equals("voice")) {
            path = Environment.getExternalStorageDirectory().toString() + File.separator + "School Voice/Voice/" + info.voiceid+".mp3"

            val fileName: String =info.voiceid+".mp3"
            val savefile: File = File(path)
            if (!savefile.exists()) {
                holder.imgdownload.setVisibility(VISIBLE)
                holder.imgplay.setVisibility(INVISIBLE)

            } else {
                holder.imgplay.setVisibility(VISIBLE)
                holder.imgdownload.setVisibility(INVISIBLE)
            }
            holder.rlvoice.setVisibility(VISIBLE)
            holder.lblcountvoice.setVisibility(VISIBLE)
            holder.imgdownload.setOnClickListener({
                DownloadVoice.downloadSampleFile(this.context, info.content!!, VOICE_FOLDER, fileName, refreshlistener)
            })
            holder.bind(info)
            Log.d("possel",info.id.toString()+ " " + selectposition.toString())
            Log.d("posselstatus",info.selectstatus.toString()+ " " + position.toString())
            holder.changeToSelect(if (selectposition==info.id && info.selectstatus==1) R.drawable.white_play else R.drawable.white_pause,info)


        } else {
            holder.rlvoice.setVisibility(GONE)
            holder.lblcountvoice.setVisibility(GONE)
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
    override fun getItemCount(): Int {
        return imagelist.size

    }


}
