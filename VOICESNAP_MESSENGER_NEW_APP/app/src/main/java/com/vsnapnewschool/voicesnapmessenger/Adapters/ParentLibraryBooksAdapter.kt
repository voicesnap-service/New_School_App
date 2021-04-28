package com.vsnapnewschool.voicesnapmessenger.Adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.Models.ImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import java.util.*


class ParentLibraryBooksAdapter(private var listname: ArrayList<ImageClass>, private val context: Context?) : RecyclerView.Adapter<ParentLibraryBooksAdapter.MyViewHolder>() {

    fun update(modelList:ArrayList<ImageClass>){
        listname = modelList
        notifyDataSetChanged()
    }
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblBookName: TextView
        internal var lblAuthorName: TextView
        internal var imgCardview: ImageView
        init {
            lblBookName = view.findViewById<View>(R.id.lblBookName) as TextView
            lblAuthorName = view.findViewById<View>(R.id.lblAuthorName) as TextView
            imgCardview = view.findViewById<View>(R.id.imgCardview) as ImageView
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_books_due, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val text_info = listname[position]
        holder.lblBookName.text = text_info.Day
        holder.lblAuthorName.text = text_info.Month
        context?.let {
            Glide.with(it)
                .load(text_info.image)
                .into(holder.imgCardview)
        }
    }
    override fun getItemCount(): Int {
        return listname.size
    }
}
