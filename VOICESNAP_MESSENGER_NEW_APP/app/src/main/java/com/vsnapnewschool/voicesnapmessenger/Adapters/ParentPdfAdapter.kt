package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Interfaces.filesImagesPdfListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.imageListener
import com.vsnapnewschool.voicesnapmessenger.Models.FilesImagePDF
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.ArrayList

class ParentPdfAdapter(private val listname: ArrayList<FilesImagePDF>, private val context: Context?, val imageListener: filesImagesPdfListener) : RecyclerView.Adapter<ParentPdfAdapter.MyViewHolder>() {
    companion object {
        var imageclickListener: filesImagesPdfListener? = null
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var pdfpath: TextView
        init {
            pdfpath = view.findViewById<View>(R.id.pdfpath) as TextView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_pdf_file, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        imageclickListener = imageListener
        imageclickListener?.onPdfFilesClick(holder,text_info)
        holder.pdfpath.text=text_info.filepath

    }
    override fun getItemCount(): Int {
        return listname.size
    }
}




