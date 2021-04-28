package com.vsnapnewschool.voicesnapmessenger.Util_Common

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.vsnapnewschool.voicesnapmessenger.R


class GifLoading {
    companion object {
        var activity: Activity? = null
        fun loading(activi: Activity?, showHide: Boolean) {
            activity = activi

            val viewGroup = (activity!!.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup
            val view = LayoutInflater.from(activity).inflate(
                R.layout.gif_loading, viewGroup, true
            )
            val loading = view.findViewById(R.id.imageView) as ImageView
            val loadingParent = view.findViewById(R.id.loadingParent) as ConstraintLayout
            if (showHide) {
                loadingParent.visibility = View.VISIBLE
                Glide.with(activity!!).load(R.drawable.school_loading).into(loading)
            }
            else {
                loadingParent.visibility = View.GONE
            }
        }
    }
}