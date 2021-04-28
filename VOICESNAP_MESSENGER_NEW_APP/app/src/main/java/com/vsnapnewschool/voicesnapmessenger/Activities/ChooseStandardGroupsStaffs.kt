package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.AllGroupsAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.AllStaffsAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.AllStandardAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkAllGroupsListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkAllStaffsListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkAllStandardListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Group
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.RecipientsType
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SchoolID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Staff
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.StaffID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.Standard
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.isSelectAll
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.previewScreens
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedFinalGroupsList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedFinalStaffsList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedFinalStandardList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.choose_specific_sections.*
import kotlinx.android.synthetic.main.choose_specific_sections.btnNext
import kotlinx.android.synthetic.main.choose_specific_sections.recycleSections
import kotlinx.android.synthetic.main.choose_standard_group_staffs.*
import retrofit2.Call
import retrofit2.Response

class ChooseStandardGroupsStaffs : BaseActivity(),checkAllGroupsListener,checkAllStandardListener,checkAllStaffsListener {

    private var StandardList = ArrayList<AllStandardData>()
    private var GroupsList = ArrayList<AllGroupsData>()
    private var StaffsList = ArrayList<AllStaffsData>()

    private var SelectedStandardList = ArrayList<AllStandardData>()
    private var SelectedGroupsList = ArrayList<AllGroupsData>()
    private var SelectedStaffsList = ArrayList<AllStaffsData>()

    var allStandardAdapter: AllStandardAdapter? = null
    var allStaffsAdapter: AllStaffsAdapter? = null
    var allGroupsAdapter: AllGroupsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_standard_group_staffs)
        enableCrashLytics()
        initializeActionBar()
        getStandardsStaffsGroups()

        isSelectAll=false
        btnNext.setOnClickListener {

            selectedFinalStandardList=SelectedStandardList
            selectedFinalGroupsList=SelectedGroupsList
            selectedFinalStaffsList=SelectedStaffsList
            previewScreens(this)

        }

        chooseSelectAll.setOnClickListener{
            if(RecipientsType == Standard){

                if (chooseSelectAll.isChecked) {
                    SelectedStandardList.clear()
                    SelectedStandardList.addAll(StandardList)
                    isSelectAll = true
                    setAllStandardAdapter()
                    setNextBtnEnable()
                } else {
                    SelectedStandardList.clear()
                    isSelectAll = false
                    setAllStandardAdapter()
                    setNextBtnEnable()
                }

            }
            else if(RecipientsType == Staff) {

                if (chooseSelectAll.isChecked) {
                    SelectedStaffsList.clear()
                    SelectedStaffsList.addAll(StaffsList)
                    isSelectAll = true
                    setAllStaffsAdapter()
                    setNextBtnEnable()
                } else {
                    SelectedStaffsList.clear()
                    isSelectAll = false
                    setAllStaffsAdapter()
                    setNextBtnEnable()
                }

            }

            else if(RecipientsType == Group){

                if (chooseSelectAll.isChecked) {
                    SelectedGroupsList.clear()
                    SelectedGroupsList.addAll(GroupsList)
                    isSelectAll = true
                    setAllGroupsAdapter()
                    setNextBtnEnable()
                } else {
                    SelectedGroupsList.clear()
                    isSelectAll = false
                    setAllGroupsAdapter()
                    setNextBtnEnable()
                }

            }
        }

    }
    private fun getStandardsStaffsGroups() {
        if(RecipientsType == Standard){
            setTitle("Choose standard")
            getAllStandardList()
        }
        else if(RecipientsType == Group){
            setTitle("Choose Groups")
            getAllGroups()
        }
        else if(RecipientsType == Staff){
            setTitle("Choose Staffs")
            getAllStaffs()
        }
    }

    private fun getAllStaffs() {

        val ModuleType:String?= Util_shared_preferences.getModuleType(this)
        val MemberType:String?= Util_shared_preferences.getMemberType(this)
        val Logintoken:String?= Util_shared_preferences.getLoginToken(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", Logintoken)
        jsonObject.addProperty("module_type", ModuleType)
        jsonObject.addProperty("member_type", MemberType)
        jsonObject.addProperty("staff_id", StaffID)
        jsonObject.addProperty("school_id", SchoolID)
        Log.d("Request", jsonObject.toString())
        GifLoading.loading(this@ChooseStandardGroupsStaffs, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getAllStaffs(jsonObject)!!
            .enqueue(object : retrofit2.Callback<AllStaffsResponse?> {
                override fun onResponse(
                    call: Call<AllStaffsResponse?>?,
                    response: Response<AllStaffsResponse?>?
                ) {
                    try {
                        GifLoading.loading(this@ChooseStandardGroupsStaffs, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getAllStaffsList", gson.toJson(response))

                        SelectedStaffsList.clear()
                        StaffsList.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                StaffsList = responseBody.data as ArrayList<AllStaffsData>

                                setAllStaffsAdapter()
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseStandardGroupsStaffs,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseStandardGroupsStaffs,
                                this@ChooseStandardGroupsStaffs.getString(
                                    R.string.Service_unavailable
                                )
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }
                override fun onFailure(call: Call<AllStaffsResponse?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseStandardGroupsStaffs, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseStandardGroupsStaffs, t.toString())
                }
            })
    }
    private fun getAllGroups() {

        val ModuleType:String?=Util_shared_preferences.getModuleType(this)
        val MemberType:String?=Util_shared_preferences.getMemberType(this)
        val Logintoken:String?= Util_shared_preferences.getLoginToken(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", Logintoken)
        jsonObject.addProperty("module_type", ModuleType)
        jsonObject.addProperty("member_type", MemberType)
        jsonObject.addProperty("staff_id", StaffID)
        jsonObject.addProperty("school_id", SchoolID)

        GifLoading.loading(this@ChooseStandardGroupsStaffs, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getAllGroups(jsonObject)!!
            .enqueue(object : retrofit2.Callback<AllGroupsResponse?> {
                override fun onResponse(
                    call: Call<AllGroupsResponse?>?,
                    response: Response<AllGroupsResponse?>?
                ) {
                    try {

                        GifLoading.loading(this@ChooseStandardGroupsStaffs, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getAllGroupsList", gson.toJson(response))
                        SelectedGroupsList.clear()
                        GroupsList.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                GroupsList = responseBody.data as ArrayList<AllGroupsData>
                                setAllGroupsAdapter()

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseStandardGroupsStaffs,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseStandardGroupsStaffs,
                                this@ChooseStandardGroupsStaffs.getString(
                                    R.string.Service_unavailable
                                )
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<AllGroupsResponse?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseStandardGroupsStaffs, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseStandardGroupsStaffs, t.toString())
                }
            })
    }
    private fun getAllStandardList() {

        val ModuleType:String?=Util_shared_preferences.getModuleType(this)
        val MemberType:String?=Util_shared_preferences.getMemberType(this)
        val Logintoken:String?= Util_shared_preferences.getLoginToken(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", Logintoken)
        jsonObject.addProperty("module_type", ModuleType)
        jsonObject.addProperty("member_type", MemberType)
        jsonObject.addProperty("staff_id", StaffID)
        jsonObject.addProperty("school_id", SchoolID)

        GifLoading.loading(this@ChooseStandardGroupsStaffs, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getAllStandardList(jsonObject)!!
            .enqueue(object : retrofit2.Callback<AllStandardResponse?> {
                override fun onResponse(
                    call: Call<AllStandardResponse?>?,
                    response: Response<AllStandardResponse?>?
                ) {

                    try {
                        GifLoading.loading(this@ChooseStandardGroupsStaffs, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getAllStandardsList", gson.toJson(response))

                        SelectedStandardList.clear()
                        StandardList.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                StandardList = responseBody.data as ArrayList<AllStandardData>
                                setAllStandardAdapter()
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseStandardGroupsStaffs,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseStandardGroupsStaffs,
                                this@ChooseStandardGroupsStaffs.getString(
                                    R.string.Service_unavailable
                                )
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<AllStandardResponse?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseStandardGroupsStaffs, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseStandardGroupsStaffs, t.toString())
                }
            })
    }

    private fun setAllGroupsAdapter() {

        allGroupsAdapter = AllGroupsAdapter(
            this@ChooseStandardGroupsStaffs,
            this@ChooseStandardGroupsStaffs,
            GroupsList,UtilConstants.isSelectAll!!
        )
        val mLayoutManager = LinearLayoutManager(this@ChooseStandardGroupsStaffs)
        recycleSections.layoutManager = mLayoutManager
        recycleSections.itemAnimator = DefaultItemAnimator()
        recycleSections.adapter = allGroupsAdapter
        allGroupsAdapter?.notifyDataSetChanged()
    }

    private fun setAllStaffsAdapter() {

        Log.d("StaffListSize",StaffsList.size.toString())
        allStaffsAdapter = AllStaffsAdapter(this@ChooseStandardGroupsStaffs, this@ChooseStandardGroupsStaffs, StaffsList, UtilConstants.isSelectAll!!)
        val mLayoutManager = LinearLayoutManager(this@ChooseStandardGroupsStaffs)
        recycleSections.layoutManager = mLayoutManager
        recycleSections.itemAnimator = DefaultItemAnimator()
        recycleSections.adapter = allStaffsAdapter

        allStaffsAdapter?.notifyDataSetChanged()
    }

    private fun setAllStandardAdapter() {
        allStandardAdapter = AllStandardAdapter(
            this@ChooseStandardGroupsStaffs,
            this@ChooseStandardGroupsStaffs,
            StandardList,UtilConstants.isSelectAll!!
        )
        val mLayoutManager = LinearLayoutManager(this@ChooseStandardGroupsStaffs)
        recycleSections.layoutManager = mLayoutManager
        recycleSections.itemAnimator = DefaultItemAnimator()
        recycleSections.adapter = allStandardAdapter
        allStandardAdapter?.notifyDataSetChanged()
    }

    override fun addGroup(groups: AllGroupsData?) {
        SelectedGroupsList.add(groups!!)
        setNextBtnEnable()

    }
    override fun removeGroup(groups: AllGroupsData?) {
        SelectedGroupsList.remove(groups!!)
        setNextBtnEnable()
    }

    override fun addStandard(standards: AllStandardData?) {
        SelectedStandardList.add(standards!!)
        setNextBtnEnable()
    }

    override fun removeStandard(standards: AllStandardData?) {

        SelectedStandardList.remove(standards!!)
        setNextBtnEnable()
    }

    override fun addStaff(staffs: AllStaffsData?) {
        SelectedStaffsList.add(staffs!!)
        Log.d("SelectedStaffsList", SelectedStaffsList.size.toString())
        setNextBtnEnable()
    }

    override fun removeStaff(staffs: AllStaffsData?) {
        SelectedStaffsList.remove(staffs!!)
        Log.d("SelectedStaffsList", SelectedStaffsList.size.toString())
        setNextBtnEnable()
    }

    private fun setNextBtnEnable() {
        if(RecipientsType == Standard){
            if(SelectedStandardList.size>0){
                btnNext.isEnabled=true
            }
            else{
                btnNext.isEnabled=false
            }
            if(StandardList.size==SelectedStandardList.size){
                chooseSelectAll.setChecked(true)
            }
            else{
                chooseSelectAll.setChecked(false)
            }

        }
        else if(RecipientsType == Group){
            if(SelectedGroupsList.size>0){
                btnNext.isEnabled=true
            }
            else{
                btnNext.isEnabled=false
            }

            if(GroupsList.size==SelectedGroupsList.size){
                chooseSelectAll.setChecked(true)
            }
            else{
                chooseSelectAll.setChecked(false)
            }

        }
        else if(RecipientsType == Staff){
            if(SelectedStaffsList.size>0){
                btnNext.isEnabled=true
            }
            else{
                btnNext.isEnabled=false
            }

            if(StaffsList.size==SelectedStaffsList.size){
                chooseSelectAll.setChecked(true)
            }
            else{
                chooseSelectAll.setChecked(false)
            }

        }
    }
}
