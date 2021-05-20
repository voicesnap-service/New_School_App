package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Adapters.ApproveLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManageLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.manageLeaveListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ApproveLeaveData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffApproveLeave
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.recyclerview_layout.*
import retrofit2.Call
import retrofit2.Response
import java.util.*


class ParentManageLeave: Fragment() {
     var manageleaveadapter: ManageLeaveAdapter?=null
    var ApproveLeaveList = ArrayList<ApproveLeaveData>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {return inflater.inflate(
        R.layout.recyclerview_layout, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adds_layout.visibility=View.GONE

        getApproveLeaveList()


        lblSeeMore.visibility=View.GONE

        (activity as BaseActivity?)!!.searchView!!.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                //filter(newText)
                return false
            }
        })

    }
    fun getApproveLeaveList() {

        val mobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val loginToken = Util_shared_preferences.getLoginToken(activity)
        val schoolID = Util_shared_preferences.getStudentSchoolID(activity)
        val StudentID = Util_shared_preferences.getStudentID(activity)
        val ClassID = Util_shared_preferences.getStudentClassID(activity)
        val SectionID = Util_shared_preferences.getStudentSectionID(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestValues.LOGIN_TOKEN, loginToken)
        jsonObject.addProperty(ApiRequestValues.MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(ApiRequestValues.SCHOOL_ID, schoolID)
        jsonObject.addProperty(ApiRequestValues.STUDENT_ID, StudentID)
        jsonObject.addProperty(ApiRequestValues.CLASS_ID, ClassID)
        jsonObject.addProperty(ApiRequestValues.SECTION_ID, SectionID)
        Log.d("LeaveListReq:", jsonObject.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.StaffApproveLeavelist(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StaffApproveLeave?> {
                override fun onResponse(
                    call: Call<StaffApproveLeave?>?,
                    response: Response<StaffApproveLeave?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("VoiceHistory:Res", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                ApproveLeaveList =
                                    responseBody.data as ArrayList<ApproveLeaveData>
                                setAdapterLeaveStatus()
                                Log.d("testApprveleave", "test")


                            } else {
                                UtilConstants.customFailureAlert(
                                    activity, responseBody.message
                                )
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                activity,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                activity,
                                activity!!.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<StaffApproveLeave?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }


    fun setAdapterLeaveStatus() {

        manageleaveadapter = ManageLeaveAdapter(ApproveLeaveList, context,
            object : manageLeaveListener {
                override fun onapproveleveClick(
                    holder: ManageLeaveAdapter.MyViewHolder,
                    item: ApproveLeaveData
                ) {


                }
            })

        val mLayoutManager = LinearLayoutManager(context)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.adapter = manageleaveadapter
        recyclerview.itemAnimator = DefaultItemAnimator()

        manageleaveadapter!!.notifyDataSetChanged()
    }



//    fun filter(s: String) {
//
//        val assignment: ArrayList<Leave_Class> = ArrayList<Leave_Class>()
//        for (d in menulist) {
//            val value: String =
//                d.status!!.toLowerCase() + d.lblstartdate!!.toLowerCase() + d.leavetype!!.toLowerCase() + d.leavereason!!.toLowerCase()
//            if (value.contains(s.toLowerCase())) {
//                assignment.add(d)
//                lblNoRecordsFound.setVisibility(View.GONE)
//            } else if (!value.contains(s) && assignment.size == 0) {
//                lblNoRecordsFound.setVisibility(View.VISIBLE)
//            }
//        }
//        if (menulist.size != 0)
//            adapterl.update(assignment);
//    }
}