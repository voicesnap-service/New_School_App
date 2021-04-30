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
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.AllStandardAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VoiceHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.voiceHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetVoiceHistory
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.VoiceHistoryData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.recyclerview_layout.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class VoiceHistory : Fragment() {
    var voiceHistoryList = ArrayList<VoiceHistoryData>()
    var voiceadapter: VoiceHistoryAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        getVoiceHistoryListApi()
        adds_layout.visibility = View.GONE
        lblSeeMore.visibility = View.GONE
        recyclerview.visibility = View.VISIBLE

    }

    fun getVoiceHistoryListApi() {

        val mobileNumber: String? = Util_shared_preferences.getMobileNumber(context as Activity?)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(activity)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", Logintoken)
        jsonObject.addProperty("mobile_number", mobileNumber)
        jsonObject.addProperty("school_id", UtilConstants.SchoolID)
        jsonObject.addProperty("staff_id", UtilConstants.StaffID)
        Log.d("VoiceHistoryRequest", jsonObject.toString())

        GifLoading.loading(activity, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.GetVoiceHistory(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetVoiceHistory?> {
                override fun onResponse(
                    call: Call<GetVoiceHistory?>?,
                    response: Response<GetVoiceHistory?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("VoiceHistory:Res", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                voiceHistoryList = responseBody.data as ArrayList<VoiceHistoryData>
                                setVoiceHistoryAdapter()
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

                override fun onFailure(call: Call<GetVoiceHistory?>?, t: Throwable?) {
                    GifLoading.loading(activity, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    fun setVoiceHistoryAdapter(){
        voiceadapter = VoiceHistoryAdapter(
            voiceHistoryList,
            (context as Activity?)!!,
            object : voiceHistoryListener {
                override fun voiceHistoryClick(
                    holder: VoiceHistoryAdapter.MyViewHolder,
                    item: VoiceHistoryData
                ) {

                    holder.imgsendvoice.setOnClickListener({
                        UtilConstants.finalPreviewVoiceMessage((context as Activity?)!!, true)
                    })

                }
            })


        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = voiceadapter
        voiceadapter?.notifyDataSetChanged()
    }

}



