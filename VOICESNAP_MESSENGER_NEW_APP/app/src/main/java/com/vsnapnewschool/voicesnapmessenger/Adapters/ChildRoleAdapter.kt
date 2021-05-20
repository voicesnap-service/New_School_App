package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.vsnapnewschool.voicesnapmessenger.Interfaces.childmemberListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ChildDetailData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants

class ChildRoleAdapter(private val childDetails: ArrayList<ChildDetailData>, private val context: Context, private  val type :Boolean,
                       val childmemberListener: childmemberListener) : RecyclerView.Adapter<ChildRoleAdapter.MyViewHolder>() {
    companion object {
        var childListener: childmemberListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblStudentName: TextView
        internal var lblRollNo: TextView
        internal var lblStandard: TextView
        internal var lblSection: TextView
        internal var lblSchoolName: TextView
        internal var imgSchoologo: ImageView
        internal var layoutchildmember: ConstraintLayout
        internal var lblDisplayMessage: TextView
        internal var btnLogin: TextView
        init {
            lblStudentName = view.findViewById<View>(R.id.lblStudentName) as TextView
            lblStandard = view.findViewById<View>(R.id.lblStandard) as TextView
            lblRollNo = view.findViewById<View>(R.id.lblRollNo) as TextView
            lblSection = view.findViewById<View>(R.id.lblSection) as TextView
            layoutchildmember = view.findViewById<View>(R.id.layoutchildmember) as ConstraintLayout
            lblSchoolName = view.findViewById<View>(R.id.lblSchoolName) as TextView
            imgSchoologo = view.findViewById<View>(R.id.imgSchoologo) as ImageView
            lblDisplayMessage = view.findViewById<View>(R.id.lblDisplayMessage) as TextView
            btnLogin = view.findViewById<View>(R.id.btnLogin) as TextView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.child_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ChildDetailData = childDetails[position]
        childListener = childmemberListener
        childListener?.onchildmemberclick(holder, ChildDetailData)
        holder.lblStudentName.setText(ChildDetailData.child_name)
        UtilConstants.textBoldContext(context,holder.lblStudentName)
        holder.lblRollNo.setText(ChildDetailData.roll_number)
        holder.lblStandard.setText(ChildDetailData.standard_name)
        holder.lblSection.setText(ChildDetailData.section_name)
        holder.lblSchoolName.setText(ChildDetailData.school_name)

        if(ChildDetailData.is_not_Allow.equals("1")){
            holder.lblDisplayMessage.visibility=View.VISIBLE
            holder.lblDisplayMessage.setText(ChildDetailData.display_message)
        }
        else{
            holder.lblDisplayMessage.visibility=View.GONE
        }
        Glide.with(context)
            .load(ChildDetailData.school_logo_url)
            .placeholder(R.drawable.img_profile)
            .error(R.drawable.img_profile)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transform(CircleCrop())
            .into(holder.imgSchoologo)

    }

    override fun getItemCount(): Int {
        return childDetails.size

    }

}