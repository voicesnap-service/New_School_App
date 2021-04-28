package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GenerateOtpCallBack
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.OtpData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.login_screen.lblForgotPassword
import kotlinx.android.synthetic.main.login_screen.txtPassword
import kotlinx.android.synthetic.main.password_screen.*

class PasswordScreen : BaseActivity(), View.OnClickListener, GenerateOtpCallBack {
    var passwordHide: Boolean?=true
    var MobileNumber:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.password_screen)
        enableCrashLytics()
        btnlogin.setOnClickListener(this)
        imgEye.setOnClickListener(this)
        lblForgotPassword.setOnClickListener(this)

        MobileNumber=Util_shared_preferences.getMobileNumber(this)
    }
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnlogin -> {
                Util_shared_preferences.putPasswordScreen(this,true)
                loginValidation()
            }

            R.id.lblForgotPassword -> {
                SchoolAPIServices.generateOTP(this,"forgot",MobileNumber,this)
            }
            R.id.imgEye ->
            {
                passwordHideandShow()
            }
        }
    }

    private fun passwordHideandShow() {
        if(passwordHide == true){
            txtPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            txtPassword.setSelection(txtPassword.text.length);
            passwordHide = false
            imgEye?.setImageResource(R.drawable.eye_visibility)
        }
        else{
            txtPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            txtPassword.setSelection(txtPassword.text.length);
            passwordHide = true
            imgEye?.setImageResource(R.drawable.eye_invisibility)

        }
    }

    private fun loginValidation() {
        if (txtPassword?.text?.isNotEmpty() == true) {
            val token:String?= Util_shared_preferences.getLoginToken(this)
            if(token?.isNotEmpty() == true){
                SchoolAPIServices.validateLoginToken(this,MobileNumber,txtPassword.text.toString(),false)
            }
            else{
                SchoolAPIServices.generateNewLoginToken(this,MobileNumber,txtPassword.text.toString())
            }
        }
        else
        {
            UtilConstants.normalToast(this,"Please enter the password")
        }
    }

    override fun callBackValue(otpData: OtpData?) {
        Log.d("callBackvalue",otpData!!.otp_message)

    }
}
