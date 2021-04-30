package com.vsnapnewschool.voicesnapmessenger.Activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.ApproveLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.ApproveLeaveListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ApproveLeaveData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StaffApproveLeave
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.ApproveLeaveId
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.approve_leave_adapter.*
import kotlinx.android.synthetic.main.approve_leave_request.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class TeacherApproveLeave : BaseActivity(), View.OnClickListener {
    var ApproveLeaveList = ArrayList<ApproveLeaveData>()
    var approveLeaveAdapter: ApproveLeaveAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.approve_leave_request)
        enableCrashLytics()

        initializeActionBar()
        setTitle(getString(R.string.title_Approve_leave))
        enableSearch(true)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        getApproveLeaveList(this)


    }

    fun getApproveLeaveList(activity: Activity?) {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(activity)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(activity)
        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestValues.LOGIN_TOKEN, Logintoken)
        jsonObject.addProperty(ApiRequestValues.MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(ApiRequestValues.SCHOOL_ID, UtilConstants.SchoolID)
        jsonObject.addProperty(ApiRequestValues.STAFF_ID, "10000620")
        Log.d("ApproveLeaveListReq:", jsonObject.toString())

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

        approveLeaveAdapter = ApproveLeaveAdapter(
           ApproveLeaveList,
            this,
            object : ApproveLeaveListener {
                override fun onapproveleveClick(
                    holder: ApproveLeaveAdapter.MyViewHolder,
                    item: ApproveLeaveData
                ) {

                    holder.btnApprove.setOnClickListener {
                        //Approve=1

                        ApproveLeaveId = item.leave_id
                        Log.d("ApproveLeaveId",ApproveLeaveId!!)
                        UtilConstants.ApproveLeaveTypeStatus = "1"

                        SchoolAPIServices.approveLeaveStatus(this@TeacherApproveLeave)

                    }
                    holder.btnReject.setOnClickListener({
                        ApproveLeaveId = item.leave_id
                        UtilConstants.ApproveLeaveTypeStatus = "2"

                        SchoolAPIServices.approveLeaveStatus(this@TeacherApproveLeave)


                    })
                }
            })

        val mLayoutManager = LinearLayoutManager(this)
        rcyleApproveleave.layoutManager = mLayoutManager
        rcyleApproveleave.adapter = approveLeaveAdapter
        approveLeaveAdapter!!.notifyDataSetChanged()
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }
}

