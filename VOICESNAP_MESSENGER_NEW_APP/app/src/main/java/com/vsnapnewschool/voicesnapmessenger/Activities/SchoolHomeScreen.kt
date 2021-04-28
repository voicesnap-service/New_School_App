package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.SchoolHomeGridAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.schoolHomeMenuClicklistener
import com.vsnapnewschool.voicesnapmessenger.Models.HomeMenus
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.GetMenuList
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.MenuListData
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.BottomMenuHome
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsAdmin
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsGroupHead
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsPrincipal
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsStaff
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_EMERGENCY
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_FEEDBACK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_IMPORTANT_INFO
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ONLINE_TEXT_BOOK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_OTHER_SERVICES
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SchoolListDetails
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.openSchoolMenuScreens
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.school_home.*
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception


class SchoolHomeScreen : BaseActivity(), View.OnClickListener {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!)) }

    private var mAdapter: SchoolHomeGridAdapter?= null
    private var mMenusList: MutableList<HomeMenus> = ArrayList()

    private var MenuList = ArrayList<MenuListData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.left_to_right,R.anim.right_to_left);
        setContentView(R.layout.school_home)
        enableCrashLytics()
        initializeActionBar()
        setTitle("Dashboard")
        setMemberTypes()
        getMenuList()
        scrollAdds(this,imageSlider)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

        if(BottomMenuHome!!){
            imgTeacherHomeMenu.setImageResource(R.drawable.teacher_home_orange)
        }else{
            imgTeacherHomeMenu.setImageResource(R.drawable.teacher_home_white)
        }

    }

    private fun setMemberTypes() {
       if(IsStaff==1){
           Util_shared_preferences.putMemberType(this,"3")
       }
        else if(IsPrincipal==1){
           Util_shared_preferences.putMemberType(this,"2")

       }
        else if(IsGroupHead==1){
           Util_shared_preferences.putMemberType(this,"1")

       }
        else if(IsAdmin==1){
           Util_shared_preferences.putMemberType(this,"6")
        }
    }

    private fun adapterView() {
        mAdapter = SchoolHomeGridAdapter(this,MenuList ,R.layout.recycle_cardview, object : schoolHomeMenuClicklistener {
            override fun onClick(holder: SchoolHomeGridAdapter.QuestionViewHolder, item: MenuListData) {
                holder.containerView?.setOnClickListener({
                    UtilConstants.MENU_TYPE = item.id.toInt()
                    Util_shared_preferences.putModuleType(this@SchoolHomeScreen,item.module)

                    if(MENU_TYPE== MENU_EMERGENCY){
                        openSchoolMenuScreens(this@SchoolHomeScreen)
                    }
                    else if(MENU_TYPE == MENU_FEEDBACK){
                        openSchoolMenuScreens(this@SchoolHomeScreen)
                    }
                    else if(MENU_TYPE == MENU_ONLINE_TEXT_BOOK){
                        openSchoolMenuScreens(this@SchoolHomeScreen)
                    }
                    else if(MENU_TYPE == MENU_IMPORTANT_INFO){
                        openSchoolMenuScreens(this@SchoolHomeScreen)
                    }
                    else if(MENU_TYPE == MENU_OTHER_SERVICES){
                        openSchoolMenuScreens(this@SchoolHomeScreen)
                    }

                    else {
                        if (SchoolListDetails != null) {
                            if (SchoolListDetails.size > 1) {
                                UtilConstants.SchoolID = SchoolListDetails.get(0).school_id
                                UtilConstants.StaffID = SchoolListDetails.get(0).staff_id
                                UtilConstants.schoolListScreen(this@SchoolHomeScreen)
                            } else if (SchoolListDetails.size == 1) {
                                UtilConstants.SchoolID = SchoolListDetails.get(0).school_id
                                UtilConstants.StaffID = SchoolListDetails.get(0).staff_id
                                UtilConstants.openSchoolMenuScreens(this@SchoolHomeScreen)
                            }
                        }

                    }

                })
            }
        })
        listHomeRecycle.layoutManager = GridLayoutManager(this,2)
        listHomeRecycle!!.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()
    }

    private fun getMenuList() {
        val MemberType:String?=Util_shared_preferences.getMemberType(this)
        val countryId:Int?=Util_shared_preferences.getCountryID(this)
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
                        Log.d("LanguageListResponse", gson.toJson(response))
                        MenuList.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                MenuList = responseBody.data as java.util.ArrayList<MenuListData>
                                adapterView()
                            }
                        }
                        else if(response?.code()==400 || response?.code()==500) {
                            val errorResponseBody = Gson().fromJson(response.errorBody()?.charStream(), StatusMessageResponse::class.java)
                            UtilConstants.handleErrorResponse(this@SchoolHomeScreen,response.code(),errorResponseBody)
                        }
                        else{
                            UtilConstants.normalToast(this@SchoolHomeScreen, this@SchoolHomeScreen?.getString(R.string.Service_unavailable))
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetMenuList?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@SchoolHomeScreen, t.toString())
                }
            })
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                if(BottomMenuHome!!){
                    UtilConstants.imgTeacherHomeIntent(this)
                }else{
                    UtilConstants.combinationHomeScreen(this)
                }
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }
}