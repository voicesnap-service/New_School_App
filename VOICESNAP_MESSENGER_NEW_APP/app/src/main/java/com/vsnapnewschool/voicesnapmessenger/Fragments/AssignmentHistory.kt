package com.vsnapnewschool.voicesnapmessenger.Fragments


import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Adapters.AssignmentHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.AssingmentHistoryCallback
import com.vsnapnewschool.voicesnapmessenger.Interfaces.AssingmentHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.assignmentDueListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ChildDetailData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetAssingmentResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffDetailData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class Assignment_HistoryFragment : Fragment(), AssingmentHistoryCallback {
    var assingmentHistoryList = ArrayList<GetAssingmentResponse.AssingmentData>()
    var ImageArrayList = ArrayList<GetAssingmentResponse.AssingmentData.ImageArray>()

    internal lateinit var assignmentHistoryAdapter: AssignmentHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)

        SchoolAPIServices.getAssingmentListApi(context as Activity, this)
    }


    override fun callBackAssingmentHistory(responseBody: GetAssingmentResponse) {

        assingmentHistoryList.clear()
        assingmentHistoryList = responseBody.data as ArrayList<GetAssingmentResponse.AssingmentData>

        assignmentHistoryAdapter = AssignmentHistoryAdapter(assingmentHistoryList, context,
            object : AssingmentHistoryListener {
                override fun onAssingmentHistoryClick(
                    holder: AssignmentHistoryAdapter.MyViewHolder,
                    item: GetAssingmentResponse.AssingmentData
                ) {
                    holder.rytDetails1.setOnClickListener({
                        UtilConstants.teacherassignmentHistoryActivity(activity, item)
                    })
                }

            })
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerHistory.layoutManager = mLayoutManager
        recyclerHistory.itemAnimator = DefaultItemAnimator()
        recyclerHistory.adapter = assignmentHistoryAdapter
    }
}
