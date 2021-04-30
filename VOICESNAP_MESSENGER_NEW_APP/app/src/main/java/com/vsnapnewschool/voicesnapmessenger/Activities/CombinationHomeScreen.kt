package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle

import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.messaging.FirebaseMessaging
import com.vsnapnewschool.voicesnapmessenger.Adapters.ChildRoleAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.childmemberListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.ChildDetailData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.BottomMenuHome
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsAdmin
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsGroupHead
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsParent
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsPrincipal
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.IsStaff
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_home_combination.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*

class CombinationHomeScreen : BaseActivity(), View.OnClickListener {
    internal lateinit var childmemberadapter: ChildRoleAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_combination)
        enableCrashLytics()
        initializeActionBar()
        setTitle("Choose Your Role")
        enableSearch(false)
        UtilConstants.textBold(this, lblRole)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnLogout?.setOnClickListener(this)
        rytSchoolLayout?.setOnClickListener(this)


        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                var FcmDeviceToken = it.result.toString()
                SchoolAPIServices.updateDeviceToken(this,FcmDeviceToken)
            }
        }

        imgTeacherHomeMenu.setImageResource(R.drawable.teacher_home_orange)
        childmemberadapter = ChildRoleAdapter(UtilConstants.ChildListDetails, this, true,
            object : childmemberListener { override fun onchildmemberclick(holder: ChildRoleAdapter.MyViewHolder, child_info: ChildDetailData) {
                    holder.layoutchildmember.setOnClickListener({
                        BottomMenuHome=false
                        if (child_info.is_not_Allow.equals("0")) {
                            Util_shared_preferences.putChildInfo(this@CombinationHomeScreen,child_info)
                            UtilConstants.parentHomeScreen(this@CombinationHomeScreen)
                        }
                    })
                }
            })
        val mLayoutManager = LinearLayoutManager(this)
        rycle_roles.layoutManager = mLayoutManager
        rycle_roles.itemAnimator = DefaultItemAnimator()
        rycle_roles.adapter = childmemberadapter
        childmemberadapter.notifyDataSetChanged()


        setLoginTypes()
        scrollAdds(this, imageSlider)
    }

    override fun onBackPressed() {
        UtilConstants.exitApplicationAlert(this@CombinationHomeScreen,"Exit")
    }

    private fun setLoginTypes() {
        if (IsPrincipal == 1 && IsParent == 1) {
            rytSchoolLayout.visibility = View.VISIBLE
            lblRole.text = "Login as Principal"
            lblMemberName.text = "Principal"
            lblSchoolName.text= UtilConstants.SchoolListDetails[0].school_name

        } else if (IsStaff == 1 && IsParent == 1) {
            rytSchoolLayout.visibility = View.VISIBLE
            lblRole.text = "Login as Staff"
            lblMemberName.text = "Staff"
            lblSchoolName.text= UtilConstants.SchoolListDetails[0].school_name
        } else if (IsGroupHead == 1 && IsParent == 1) {
            rytSchoolLayout.visibility = View.VISIBLE
            lblRole.text = "Login as Group Head"
            lblMemberName.text = "Group head"
            lblSchoolName.text= UtilConstants.SchoolListDetails[0].school_name
        } else if (IsAdmin == 1 && IsParent == 1) {
            rytSchoolLayout.visibility = View.VISIBLE
            lblRole.text = "Login as Admin"
            lblMemberName.text = "Admin"
            lblSchoolName.text= UtilConstants.SchoolListDetails[0].school_name
        } else {
            rytSchoolLayout.visibility = View.GONE
            lblMemberName.text = "Parent"
            setTitle("Choose Your child")
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.rytSchoolLayout -> {
                BottomMenuHome=false
                UtilConstants.schoolHomeScreen(this@CombinationHomeScreen)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }

        }
    }
}
