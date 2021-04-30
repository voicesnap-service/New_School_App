package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.VoiceMessagesClickListener
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceData
import me.jagar.chatvoiceplayerlibrary.PlayerVisualizerSeekbar
import java.util.ArrayList

class ParentVoiceAdapter(private var listname: ArrayList<GetVoiceData>, private val context: Context, val textClickListener: VoiceMessagesClickListener) : RecyclerView.Adapter<ParentVoiceAdapter.MyViewHolder>() {
    companion object {
        var clickListener: VoiceMessagesClickListener? = null
    }

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
        internal var rytContainer: RelativeLayout
        internal var imgwave: PlayerVisualizerSeekbar

        init {
            lblVoiceTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            lblDuration = view.findViewById<View>(R.id.lblDuration) as TextView
            lblCreatedBySort = view.findViewById<View>(R.id.lblCreatedBySort) as TextView
            lblCreatedOn = view.findViewById<View>(R.id.lblCreatedOn) as TextView
            lblNew = view.findViewById<View>(R.id.lblNew) as TextView
            imgplay = view.findViewById<View>(R.id.imgplay) as ImageView
            imgwave = view.findViewById<View>(R.id.imgwave) as PlayerVisualizerSeekbar
            rytContainer = view.findViewById<View>(R.id.rytContainer) as RelativeLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_communiacation_messages, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val voice_info = listname[position]
        clickListener = textClickListener
        clickListener?.onVoiceClick(holder,voice_info)
        holder.lblVoiceTitle.text = voice_info.description
        holder.lblCreatedBySort.text=voice_info.created_by_short
        holder.lblCreatedOn.text=voice_info.created_on

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
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}