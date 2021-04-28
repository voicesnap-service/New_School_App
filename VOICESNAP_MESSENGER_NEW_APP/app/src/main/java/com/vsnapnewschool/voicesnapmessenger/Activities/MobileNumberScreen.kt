package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.mobile_number_screen.*


class MobileNumberScreen : BaseActivity(), View.OnClickListener {

    private var mobileLength:Int?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mobile_number_screen)
        enableCrashLytics()
        btnNext.setOnClickListener(this)

        mobileLength = Util_shared_preferences.getCountryMobileLength(this)
        txtPhoneNumber.filters = arrayOf(InputFilter.LengthFilter(mobileLength!!))
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnNext -> {
                if (txtPhoneNumber.text.length == mobileLength) {
                    Util_shared_preferences.putMobileNumber(this,  txtPhoneNumber?.text.toString())
                    SchoolAPIServices.checkMobileNumber(this)
                } else {
                    UtilConstants.normalToast(this, "Enter valid mobile number")
                }
            }
        }
    }
}