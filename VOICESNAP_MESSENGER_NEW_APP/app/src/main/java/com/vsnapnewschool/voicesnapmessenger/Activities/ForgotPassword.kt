package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.forgot_password_screen.*
import kotlinx.android.synthetic.main.otp_screen.imgBack

class ForgotPassword : AppCompatActivity(), View.OnClickListener {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password_screen)
        imgBack.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        Util_shared_preferences.putForgotPassword(this@ForgotPassword,false)

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnNext -> {
                if(!txtOTP.text.toString().isEmpty()) {
                    val MobileNumber:String?=Util_shared_preferences.getMobileNumber(this)
                    SchoolAPIServices.validateOTP(this, MobileNumber, txtOTP.text.toString())
                }
                else{
                    UtilConstants.normalToast(this,"Please enter the OTP")
                }
            }
            R.id.imgBack -> {
                onBackPressed()

            }
        }
    }
}