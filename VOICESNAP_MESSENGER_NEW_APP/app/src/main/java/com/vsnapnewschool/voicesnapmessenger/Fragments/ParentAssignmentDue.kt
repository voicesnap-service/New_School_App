package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.razorpay.BaseUtils.getSystemService
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentAssignmentDueAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetNoticeBoardCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetParentAssingmentCallback
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReadStatusCallBacak
import com.vsnapnewschool.voicesnapmessenger.Interfaces.assignmentDueListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetParentAssignmentResponse
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetPdfFilesResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*


class ParentAssignmentDue : Fragment(),View.OnClickListener, GetParentAssingmentCallback,
    ReadStatusCallBacak {
    var assingmentDueList = ArrayList<GetParentAssignmentResponse.AssingmentDueData>()
    var type:Boolean=true
    internal lateinit var assignmentDueAdapter: ParentAssignmentDueAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    { return inflater.inflate(R.layout.recyclerview_layout, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        UtilConstants.CLICKED_SEE_MORE = false
        StudentAPIServices.getParentAssignment(activity,this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )



//        (activity as BaseActivity?)!!.searchView!!.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//
//                return false
//            }
//            override fun onQueryTextChange(newText: String): Boolean {
//                filter(newText)
//
//                return false
//            }
//        })


    }



    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgchat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(activity)
            }
            R.id.imgSettings -> {
                UtilConstants.imgProfileIntent(activity)
            }
        }


    }

    override fun callBackAssignmentDue(responseBody: GetParentAssignmentResponse) {
        assingmentDueList.clear()
        assingmentDueList = responseBody.data as ArrayList<GetParentAssignmentResponse.AssingmentDueData>
        Log.d("assingmentDueList", assingmentDueList.size.toString())
        assignmentDueAdapter = ParentAssignmentDueAdapter(assingmentDueList, activity, type, object : assignmentDueListener {
            override fun onassignmentClick(holder: ParentAssignmentDueAdapter.MyViewHolder, item: GetParentAssignmentResponse.AssingmentDueData) {
                holder.rytDetails1.setOnClickListener({
                        UtilConstants.parentAssignmentView(context as Activity, item)

                        if (item.is_archive == 0) {
                            StudentAPIServices.updateReadStatus(
                                activity,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_NORMAL, this@ParentAssignmentDue
                            )
                        } else {
                            StudentAPIServices.updateReadStatus(
                                activity,
                                item.header_id,
                                item.detail_id,
                                UtilConstants.API_SEE_MORE,
                                this@ParentAssignmentDue
                            )
                        }
                 //   }

                })
            }
        })
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = assignmentDueAdapter
    }

    override fun callBackAssignmentDueArchive(responseBody: GetParentAssignmentResponse) {
        UtilConstants.CLICKED_SEE_MORE = true
        lblSeeMore.visibility = View.GONE
        assingmentDueList.addAll(responseBody.data)
        assignmentDueAdapter.notifyDataSetChanged()
    }



    override fun callBackReadStatus(updateStatus: Boolean?) {
        if (updateStatus == true) {
            getAssingmentAfterReadList()
        }
    }

    private fun getAssingmentAfterReadList() {
        StudentAPIServices.getParentAssignment(activity,this,
            UtilConstants.API_NORMAL,
            recyclerview,
            shimmerFrameLayout
        )
    }
    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmerAnimation()
        super.onPause()
    }
    override fun onCreateContextMenu(
        menu: ContextMenu, v: View,
        menuInfo: ContextMenu.ContextMenuInfo?) {
        menu.add(0, v.id, 0, "Copy")
        val textView = v as TextView
        val manager = activity!!.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("text", textView.text)
        manager.setPrimaryClip(clipData)
    }

}
