package com.vsnapnewschool.voicesnapmessenger.Activities

import Data
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentHomeGridAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentHomeMenuClickListener
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetMenuList
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MenuListData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.openParentMenuScreens
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.school_home.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class ParentHomeScreen : BaseActivity(), View.OnClickListener {
    private lateinit var recyclerView: RecyclerView
    var dataList = ArrayList<Data>()
    private var mAdapter: ParentHomeGridAdapter? = null
    private var MenuList = ArrayList<MenuListData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.parent_home)
        enableCrashLytics()
        parentActionbar()
        setTitle("Hello Parent")
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        Util_shared_preferences.putMemberType(this, "0")
        recyclerView = findViewById(R.id.recyclerView)
        getMenuList()
        scrollAdds(this, imageSlider)
        if(UtilConstants.BottomMenuHome!!){
            imgHomeMenu.setImageResource(R.drawable.prnt_group)

        }else{
            imgHomeMenu.setImageResource(R.drawable.prnt_group_white)

        }



    }

    private fun getMenuList() {
        val MemberType: String? = Util_shared_preferences.getMemberType(this)
        val countryId: Int? = Util_shared_preferences.getCountryID(this@ParentHomeScreen)
        val jsonObject = JsonObject()
        jsonObject.addProperty("country_id", countryId.toString())
        jsonObject.addProperty("language_id", "1")
        jsonObject.addProperty("member_type", MemberType)
        Log.d("Request", jsonObject.toString())
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getMenuListByCountry(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetMenuList?> {
                override fun onResponse(
                    call: Call<GetMenuList?>?,
                    response: Response<GetMenuList?>?
                ) {
                    try {
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getMenuList", gson.toJson(response))
                        MenuList.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                MenuList = responseBody.data as java.util.ArrayList<MenuListData>
                                adapterView()
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ParentHomeScreen,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ParentHomeScreen,
                                this@ParentHomeScreen?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetMenuList?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ParentHomeScreen, t.toString())
                }
            })
    }

    private fun adapterView() {
        mAdapter = ParentHomeGridAdapter(
            this,
            MenuList,
            R.layout.menu_items_parent,
            object : parentHomeMenuClickListener {
                override fun onClick(
                    holder: ParentHomeGridAdapter.QuestionViewHolder,
                    item: MenuListData
                ) {
                    holder.containerView.setOnClickListener({
                        UtilConstants.PARENT_MENU_TYPE = item.id.toInt()
                        Util_shared_preferences.putModuleType(this@ParentHomeScreen, item.module)
                        openParentMenuScreens(this@ParentHomeScreen)


                    })
                }
            })
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgchat -> {
            }
            R.id.imgHomeMenu -> {

                if(UtilConstants.BottomMenuHome!!){
                    UtilConstants.imgHomeIntent(this)

                }else{
                    UtilConstants.combinationHomeScreen(this)

                }


            }
            R.id.imgSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }
}