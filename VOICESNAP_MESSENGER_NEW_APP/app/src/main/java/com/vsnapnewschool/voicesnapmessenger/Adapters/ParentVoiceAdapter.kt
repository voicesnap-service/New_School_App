package com.vsnapnewschool.voicesnapmessenger.Adapters

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
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.VoiceMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceData
import com.vsnapnewschool.voicesnapmessenger.Utils.DownloadVoice
import me.jagar.chatvoiceplayerlibrary.FileUtils
import me.jagar.chatvoiceplayerlibrary.PlayerVisualizerSeekbar
import java.io.File
import java.util.*

class ParentVoiceAdapter(private var listname: ArrayList<GetVoiceData>, private val context: Context, var mediaPlayer: MediaPlayer,
                         val textClickListener: VoiceMessagesClickListener) : RecyclerView.Adapter<ParentVoiceAdapter.MyViewHolder>() {
    companion object {
        var clickListener: VoiceMessagesClickListener? = null
    }
    var mediaFileLengthInMilliseconds = 0
    var handler = Handler()
    var path: String? = null
    var pause:Boolean = false
    var prevposition:String = "0"
    var selectposition :String= "0"
    private val VOICE_FOLDER: String? = "New School/Voice/"

    fun update(updateTextList: ArrayList<GetVoiceData>) {
        listname = updateTextList
        notifyDataSetChanged()
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblVoiceTitle: TextView
        internal var lblDuration: TextView
        internal var lblCreatedBySort: TextView
        internal var lblCreatedOn: TextView
        internal var lblNew: TextView
        internal var imgplay: ImageView
        internal var imgdownload: ImageView
        internal var rytContainer: RelativeLayout
        internal var imgwave: PlayerVisualizerSeekbar

        init {
            lblVoiceTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            lblDuration = view.findViewById<View>(R.id.lblDuration) as TextView
            lblCreatedBySort = view.findViewById<View>(R.id.lblCreatedBySort) as TextView
            lblCreatedOn = view.findViewById<View>(R.id.lblCreatedOn) as TextView
            lblNew = view.findViewById<View>(R.id.lblNew) as TextView
            imgplay = view.findViewById<View>(R.id.imgplay) as ImageView
            imgdownload = view.findViewById<View>(R.id.imgDownload) as ImageView
            imgwave = view.findViewById<View>(R.id.imgwave) as PlayerVisualizerSeekbar
            rytContainer = view.findViewById<View>(R.id.rytContainer) as RelativeLayout
        }

        fun bind(voiceInfo: GetVoiceData) {
            imgplay.setOnClickListener({
                prevposition=selectposition
                selectposition=voiceInfo.header_id
                if(!prevposition.equals("0")){
                    if(prevposition.equals(selectposition)){
                        Log.d("true",prevposition+"-"+selectposition)

                        if(!mediaPlayer.isPlaying) {
                            Log.d("true","start")
                            if(pause==true){
                                mediaPlayer.seekTo(mediaPlayer.currentPosition)
                                mediaPlayer.start()
                            }
                            else{
                                start(voiceInfo)
                            }
                            pause=false
                        }
                        else {
                            Log.d("true","pause")
                            pause(voiceInfo)
                            pause=true

                        }


                    }
                    else{
                        Log.d("false","stopstart")

                        stop(voiceInfo)
                        if (!mediaPlayer.isPlaying) {
                            start(voiceInfo)
                        }
                    }
                }
                else{
                    Log.d("new","new")

                    if (!mediaPlayer.isPlaying) {
                        Log.d("new","start")
                        start(voiceInfo)
                    }
                    else{
                        Log.d("new","stop")
                        stop(voiceInfo)
                    }
                }
                notifyItemRangeChanged(0,listname.size)

            })
        }
        fun primarySeekBarProgressUpdater(fileLength: Int,id:String) {

            if(selectposition.equals(id)) {
                if (mediaPlayer.isPlaying) {
                    val notification = Runnable {
                        lblDuration.setText(milliSecondsToTimer(mediaPlayer.currentPosition.toLong()))
                        imgwave.setProgress(mediaPlayer.currentPosition)
                        imgwave.updatePlayerPercent(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration)
                        primarySeekBarProgressUpdater(fileLength, id)
                    }
                    handler.postDelayed(notification, 1000)
                }

                else{
                    val notification = Runnable {
                        lblDuration.setText(milliSecondsToTimer(mediaPlayer.currentPosition.toLong()))
                        imgwave.setProgress(mediaPlayer.currentPosition)
                        imgwave.updatePlayerPercent(mediaPlayer.currentPosition.toFloat() / mediaPlayer.duration)
                        primarySeekBarProgressUpdater(fileLength, id)
                    }
                    handler.postDelayed(notification, 1000)

                }

            }
            else{
                lblDuration.setText("00:00")
                imgwave.setProgress(0)
                imgwave.updatePlayerPercent(0F)
//                primarySeekBarProgressUpdater(0, id)

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
                    if (imgwave.getVisibility() == View.VISIBLE) {
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
        fun initialimgwaveset(b: Boolean, voiceInfo: GetVoiceData) {
            if(b){
                seekbarwave(imgwave,voiceInfo)
            }
            else{
                imgwave.visibility=View.GONE
                lblDuration.visibility=View.GONE
            }
        }
        @RequiresApi(Build.VERSION_CODES.M)
        fun imgwaveupdate(
            visibility: Boolean,
            voiceInfo: GetVoiceData
        ){
            if(visibility){
                lblDuration.visibility=View.VISIBLE
                imgwave.setOnSeekBarChangeListener(seekBarListener);

                if(mediaPlayer.isPlaying){
                    mediaFileLengthInMilliseconds=mediaPlayer.duration
                    primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds,voiceInfo.header_id)
                    imgwave.visibility=View.VISIBLE
                    lblDuration.visibility=View.VISIBLE
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
                    complete(voiceInfo,imgplay,lblDuration,imgwave)


                }
                else{
                    mediaFileLengthInMilliseconds=mediaPlayer.duration
                    primarySeekBarProgressUpdater(mediaFileLengthInMilliseconds,voiceInfo.header_id)
                    imgwave.visibility=View.VISIBLE
                    lblDuration.visibility=View.VISIBLE
                    imgwave.getProgressDrawable().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.getThumb().setColorFilter(
                        context.getResources().getColor(android.R.color.transparent),
                        PorterDuff.Mode.SRC_IN
                    )
                    imgwave.setMax(mediaPlayer.getDuration())
//                    imgwave.updatePlayerPercent(mediaPlayer.currentPosition)
                    imgwave.setProgress(mediaPlayer.currentPosition)
                    imgwave.updateVisualizer(FileUtils.fileToBytes(File(path)))
                    imgwave.setColors(
                        context.getColor(R.color.white),
                        context.getColor(R.color.black)
                    )

                }
            }
//            else{
//                imgwave.visibility=View.GONE
//                lblDuration.visibility=View.GONE
//            }

        }
        @RequiresApi(Build.VERSION_CODES.M)
        fun changeToSelect(resid: Int, voiceInfo: GetVoiceData){
            imgplay.setImageResource(resid)


        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_communiacation_messages, parent, false)
        return MyViewHolder(itemView)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val voice_info = listname[position]
        clickListener = textClickListener
        clickListener?.onVoiceClick(holder,voice_info)
        holder.lblVoiceTitle.text = voice_info.description
        holder.lblCreatedBySort.text=voice_info.created_by_short
        holder.lblCreatedOn.text=voice_info.created_on
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .path + File.separator + VOICE_FOLDER + voice_info.detail_id + ".mp3"
        }
        else{
            path = Environment.getExternalStorageDirectory()
                .toString() + File.separator + VOICE_FOLDER + voice_info.detail_id + ".mp3"
        }
        val fileName: String =voice_info.detail_id+".mp3"
        val savefile: File = File(path)

        if (!savefile.exists()) {
            holder.imgplay.visibility=View.GONE
            holder.lblDuration.visibility=View.GONE
            holder.imgwave.visibility=View.GONE
            holder.imgdownload.visibility=View.VISIBLE
            holder.imgdownload.setImageResource(R.drawable.download_img)


        } else {
            holder.imgdownload.visibility=View.GONE
            holder.imgplay.visibility=View.VISIBLE
            holder.imgplay.setImageResource(R.drawable.white_pause)
        }
        holder.imgdownload.setOnClickListener({
            Log.d("click","downloadclick")
            DownloadVoice.downloadSampleFile(this.context, voice_info.voice_file, VOICE_FOLDER, fileName, textClickListener)
        })
        if(voice_info.is_emergency == 0){
            holder.rytContainer?.setBackgroundResource(R.drawable.rect_blue_white)
            holder.lblCreatedBySort?.setBackgroundResource(R.drawable.rect_blue_white)
        }
        else{
            holder.rytContainer?.setBackgroundResource(R.drawable.rec_red_white)
            holder.lblCreatedBySort?.setBackgroundResource(R.drawable.rec_red_white)
        }

        if(voice_info.app_viewed == 1){
            holder.lblNew.visibility= View.GONE
        }
        else{
            holder.lblNew.visibility= View.VISIBLE
        }

        holder.bind(voice_info)
        holder.initialimgwaveset(if(savefile.exists()) true else false , voice_info)
        holder.imgwaveupdate(if(selectposition.equals(voice_info.header_id) && mediaPlayer.isPlaying || selectposition.equals(voice_info.header_id)&& pause==true ) true else false ,voice_info)
        holder.changeToSelect(if (selectposition.equals(voice_info.header_id)&& mediaPlayer.isPlaying ) R.drawable.white_play else R.drawable.white_pause,voice_info)


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

    fun start(voiceInfo: GetVoiceData){
        Log.d("start","start")
        if (!mediaPlayer.isPlaying) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                path =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        .path + File.separator + VOICE_FOLDER + voiceInfo.detail_id + ".mp3"
                Log.d("pathR", path!!)

            }
            else{
                path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + VOICE_FOLDER + voiceInfo.detail_id + ".mp3"
                Log.d("path", path!!)

            }
            mediaPlayer.reset()
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepare()
            mediaFileLengthInMilliseconds = (mediaPlayer.duration / 1000.0).toInt()
            mediaPlayer.start()

        }
    }

    fun stop(voiceInfo: GetVoiceData){
        Log.d("stop","stop")
        mediaPlayer.stop()
        mediaPlayer.seekTo(0)

    }
    fun pause(voiceInfo: GetVoiceData){
        Log.d("pause","pause")
        mediaPlayer.pause()
    }
    fun complete(
        voiceInfo: GetVoiceData,
        imgplay: ImageView,
        lblDuration: TextView,
        imgwave: PlayerVisualizerSeekbar
    ){
        Log.d("complete","complete")

        mediaPlayer.setOnCompletionListener {
            imgplay.setImageResource(R.drawable.white_pause)
            mediaPlayer.seekTo(0)
            imgwave.setProgress(0)
            imgwave.updatePlayerPercent(0F)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun seekbarwave(imgwave:PlayerVisualizerSeekbar,voiceInfo: GetVoiceData){
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            path =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .path + File.separator + VOICE_FOLDER + voiceInfo.detail_id + ".mp3"
            Log.d("pathR", path!!)

        }
        else{
            path = Environment.getExternalStorageDirectory()
                .toString() + File.separator + VOICE_FOLDER + voiceInfo.detail_id + ".mp3"
            Log.d("path", path!!)

        }
        imgwave.setVisibility(View.VISIBLE)
        imgwave.getProgressDrawable().setColorFilter(
            context!!.getResources().getColor(android.R.color.transparent),
            PorterDuff.Mode.SRC_IN
        )
        imgwave.getThumb().setColorFilter(
            context.getResources().getColor(android.R.color.transparent),
            PorterDuff.Mode.SRC_IN
        )
        imgwave.setMax(mediaPlayer.duration)
        imgwave.setProgress(0)
        imgwave.updatePlayerPercent(0F)

        imgwave.updateVisualizer(FileUtils.fileToBytes(File(path!!)))
        imgwave.setColors(
            context.getColor(R.color.white),
            context.getColor(R.color.black)
        )
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}