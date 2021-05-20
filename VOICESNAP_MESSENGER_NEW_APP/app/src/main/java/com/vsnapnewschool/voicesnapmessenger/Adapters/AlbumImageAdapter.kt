package com.vsnapnewschool.voicesnapmessenger.Adapters


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.vsnapnewschool.voicesnapmessenger.Models.SelectedFilesClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.filetype
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.filetypePdf
import de.hdodenhof.circleimageview.CircleImageView


class AlbumImageAdapter(private val imagelist: ArrayList<SelectedFilesClass>, val FileType: String, private val context: Context?, val btnlistener: AlbumImageAdapter.BtnClickListener) : RecyclerView.Adapter<AlbumImageAdapter.MyViewHolder>() {

    companion object {
        var mClickListener: BtnClickListener? = null
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal var lblSelectedPath: TextView
        internal var lblVideoPath: TextView
        internal var imgPhoto: CircleImageView
        internal var imgClose: ImageView
        internal var imgPDF: ImageView
        internal var imgPdfclose: ImageView
        internal var imgVideoclose: ImageView
        internal var PDFLayout: ConstraintLayout
        internal var ImageLayout: ConstraintLayout
        internal var VideoLayout: FrameLayout
        internal var imgthumb: ShapeableImageView

        init {
            lblSelectedPath = view.findViewById<View>(R.id.lblSelectedPath) as TextView
            imgPhoto = view.findViewById<View>(R.id.imgPhoto) as CircleImageView
            imgClose = view.findViewById<View>(R.id.imgClose) as ImageView
            imgthumb = view.findViewById<View>(R.id.imgthumb) as ShapeableImageView
            imgPDF = view.findViewById<View>(R.id.imgPDF) as ImageView
            imgPdfclose = view.findViewById<View>(R.id.imgPdfclose) as ImageView
            PDFLayout = view.findViewById<View>(R.id.PDFLayout) as ConstraintLayout
            ImageLayout = view.findViewById<View>(R.id.ImageLayout) as ConstraintLayout
            VideoLayout = view.findViewById<View>(R.id.VideoLayout) as FrameLayout
            imgVideoclose = view.findViewById<View>(R.id.imgVideoclose) as ImageView
            lblVideoPath = view.findViewById<View>(R.id.lblVideoPath) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.selected_imagefiles_design, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mClickListener = btnlistener
        val text_info:SelectedFilesClass = imagelist[position]
        Log.d("adapterfileType",FileType)



        if(text_info.contentype.equals("pdf")) {
            holder.ImageLayout.visibility = View.VISIBLE
            if (context != null) {
                Glide.with(context)
                    .load(R.drawable.pdf)
                    .into(holder.imgPhoto)
                holder.lblSelectedPath.text = text_info.filepath

            }
        }
        if (text_info.contentype.equals("image")) {
            holder.ImageLayout.visibility = View.VISIBLE
            if (context != null) {
                Glide.with(context)
                    .load(text_info.filepath)
                    .into(holder.imgPhoto)

//                val Filename = File.separator + text_info.substring(text_info.lastIndexOf('/') + 1)
//                val separated = Filename.split("/").toTypedArray()
//                val name = separated[0] //"/"
//                val FileName = separated[1]
//                holder.lblSelectedPath.text =FileName
                holder.lblSelectedPath.text =text_info.filepath

            }
        //}
        }
        if(FileType.equals("video")) {
            holder.ImageLayout.visibility = View.GONE
            holder.PDFLayout.visibility=View.GONE
            holder.VideoLayout.visibility=View.VISIBLE
            holder.lblVideoPath.visibility=View.VISIBLE
            holder.imgVideoclose.visibility=View.VISIBLE
            if (context != null) {
                Glide.with(context)
                    .asBitmap()
                    .load(text_info)
                    .into(holder.imgthumb)
            }
            holder.lblSelectedPath.text = text_info.filepath
        }
        holder.imgClose.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                notifyDataSetChanged()
                if (mClickListener != null)
                    mClickListener?.onBtnClick(position)
            }
        })
        holder.imgPdfclose.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                notifyDataSetChanged()
                if (mClickListener != null)
                    mClickListener?.onBtnClick(position)
            }
        })
        holder.imgVideoclose.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                notifyDataSetChanged()
                if (mClickListener != null)
                    mClickListener?.onBtnClick(position)
            }
        })
    }

    interface BtnClickListener {
        fun onBtnClick(position: Int)
    }

    override fun getItemCount(): Int {
        return imagelist.size

    }
}














