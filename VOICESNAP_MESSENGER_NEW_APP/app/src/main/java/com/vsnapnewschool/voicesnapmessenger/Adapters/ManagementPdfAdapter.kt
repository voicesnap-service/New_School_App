package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.managementPdfListener
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MessageFromManagementPdfResponse
import java.util.ArrayList

class ManagementPdfAdapter  (private var pdflist: ArrayList<MessageFromManagementPdfResponse.PdfData>,
                             private val context: Context?,
                             private val type: String,
                             val pdflistener: managementPdfListener) : RecyclerView.Adapter<ManagementPdfAdapter.MyViewHolder>() {
    fun update(modelList: ArrayList<MessageFromManagementPdfResponse.PdfData>){
        pdflist = modelList
        notifyDataSetChanged()
    }
    companion object {
        var pdflistlistener: managementPdfListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var content: TextView
        internal var time: TextView
        internal var lblTitle: TextView
        internal var lblSentBy: TextView
        internal var lblNew: TextView
        internal var circular: ConstraintLayout
        internal var CircularLayout: ConstraintLayout
        init {
            content = view.findViewById<View>(R.id.lblcontent) as TextView
            time = view.findViewById<View>(R.id.lblsent) as TextView
            lblTitle = view.findViewById<View>(R.id.lblTitle) as TextView
            lblSentBy = view.findViewById<View>(R.id.lblSentBy) as TextView
            lblNew = view.findViewById<View>(R.id.lblNew) as TextView
            circular = view.findViewById<View>(R.id.circular) as ConstraintLayout
            CircularLayout = view.findViewById<View>(R.id.CircularLayout) as ConstraintLayout
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_circular_card, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = pdflist[position]
        holder.run {
            pdflistlistener = pdflistener
            pdflistlistener?.onpdfclickListener(holder,text_info)
            content.text = text_info.description
            time.text = text_info.createdon
            lblSentBy.text = text_info.created_by
            holder.lblSentBy.setTextColor(Color.parseColor("#fb6b22"))



            if(text_info.app_viewed == 1){
                holder.lblNew.visibility= View.GONE
            }
            else{
                holder.lblNew.visibility= View.VISIBLE
            }
        }
    }
    override fun getItemCount(): Int {
        return pdflist.size

    }
}
