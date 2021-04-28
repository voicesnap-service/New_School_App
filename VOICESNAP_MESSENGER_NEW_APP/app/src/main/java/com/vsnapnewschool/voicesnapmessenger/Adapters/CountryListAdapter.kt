package com.vsnapnewschool.voicesnapmessenger.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.CountryData
import kotlinx.android.synthetic.main.country_list_item.view.*
import java.util.*

class CountryListAdapter(
    private val countryDataList: ArrayList<CountryData>,
    private val mListener: OnItemClickListener?,private val context: Context
) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    private var checkedPosition = -1
    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    override fun getItemCount() = countryDataList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.country_list_item,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val singleCountryData = countryDataList[position]
        holder.lblCountryNAme.setText(singleCountryData.country_name)

        Glide.with(context)
            .load(singleCountryData.country_logo)
            .placeholder(R.drawable.indian_flag)
            .error(R.drawable.indian_flag)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .transform(CircleCrop())
            .into(holder.countyrImage)

        holder.checkbox.setChecked(position == checkedPosition)
        holder.checkbox.setOnClickListener {

            if (position == checkedPosition) {
                holder.checkbox.setChecked(false)
                mListener?.onRemoveClick(holder.itemView, singleCountryData)
                checkedPosition = -1
            } else {
                checkedPosition = position
                mListener?.onItemClick(holder.itemView, singleCountryData)
                notifyDataSetChanged()
            }
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countyrImage: ImageView = itemView.imgFlag
        val checkbox: CheckBox = itemView.chooseCheck
        val lblCountryNAme: TextView = itemView.lblCountryNAme
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, viewModel: CountryData)

        fun onRemoveClick(view: View, viewModel: CountryData)
    }
    }