package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Interfaces.circularHistorylistener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class CircularHistoryAdapter(
    private var pdflist: ArrayList<GetPdfFilesResponse.PdfData>,
    private val context: Context?,
    private val type: String,
    val ciruclarlistener:circularHistorylistener) : RecyclerView.Adapter<CircularHistoryAdapter.MyViewHolder>() {
    fun update(modelList:ArrayList<GetPdfFilesResponse.PdfData>){
        pdflist = modelList
        notifyDataSetChanged()
    }
    companion object {
        var circularhistorylistener: circularHistorylistener? = null
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
            circularhistorylistener = ciruclarlistener
            circularhistorylistener?.oncircularclickListener(holder,text_info)
            content.text = text_info.description
            time.text = text_info.createdon
            lblSentBy.text = text_info.created_by


            if (type.equals("0")) {
                CircularLayout.setBackgroundResource(R.drawable.blue_shadow)

            }

            if(text_info.app_viewed == 1){
                holder.lblNew.visibility=View.GONE
            }
            else{
                holder.lblNew.visibility=View.VISIBLE
            }
        }
    }
    override fun getItemCount(): Int {
        return pdflist.size

    }
}
