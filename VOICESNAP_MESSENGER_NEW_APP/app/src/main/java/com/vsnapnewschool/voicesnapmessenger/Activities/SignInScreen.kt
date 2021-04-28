package com.vsnapnewschool.voicesnapmessenger.Activities

import android.graphics.Typeface
import android.os.Bundle
import android.text.InputFilter
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import com.vsnapnewschool.voicesnapmessenger.BioMetric.BiometricCallback
import com.vsnapnewschool.voicesnapmessenger.BioMetric.BiometricDialogV23
import com.vsnapnewschool.voicesnapmessenger.BioMetric.BiometricManager
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GenerateOtpCallBack
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.OtpData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.choose_receipients_popup.view.*
import kotlinx.android.synthetic.main.login_screen.*
import kotlinx.android.synthetic.main.login_screen.txtPhoneNumber
import kotlinx.android.synthetic.main.mobile_number_screen.*

class SignInScreen : BaseActivity(), View.OnClickListener, GenerateOtpCallBack {

    var xyz: String?=null
    var passwordHide: Boolean?=true
    var MobileNumber:String?=null
    var Password:String?=null
    var mobileLength:Int?=null
    var biometricCallback:BiometricCallback?=null
    var mBiometricManager: BiometricManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
        setContentView(R.layout.login_screen)
        enableCrashLytics()
        setTextStyle()
        btnLogin?.setOnClickListener(this)
        lblForgotPassword?.setOnClickListener(this)
        imgEye?.setOnClickListener(this)
        mobileLength = Util_shared_preferences.getCountryMobileLength(this)
        txtPhoneNumber.filters = arrayOf(InputFilter.LengthFilter(mobileLength!!))

        val userAlreadyLoggedIn :Boolean?=Util_shared_preferences.getFingerCheck(this@SignInScreen)
        if(userAlreadyLoggedIn == true) {
            checkBiometric()
        }

    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
      if(hasFocus){
         // checkBiometric()
      }
    }
    private fun checkBiometric() {
         biometricCallback = object : BiometricCallback {
            override fun onSdkVersionNotSupported() {
                Log.d("FP", "onSdkVersionNotSupported")

            }

            override fun onBiometricAuthenticationNotSupported() {
                Log.d("FP", "onBiometricAuthenticationNotSupported")

            }

            override fun onBiometricAuthenticationNotAvailable() {
                Log.d("FP", "onBiometricAuthenticationNotAvailable")

            }

            override fun onBiometricAuthenticationPermissionNotGranted() {
                Log.d("FP", "onBiometricAuthenticationPermissionNotGranted")

            }

            override fun onBiometricAuthenticationInternalError(error: String) {
                Toast.makeText(this@SignInScreen, error, Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationFailed() {
                Log.d("FP", "onAuthenticationFailed")

            }

            override fun onAuthenticationCancelled() {
                Log.d("FP", "onAuthenticationCancelled")
            }

            override fun onAuthenticationSuccessful() {
                Log.d("FP", "onAuthenticationSuccessful")

                val token:String?=Util_shared_preferences.getLoginToken(this@SignInScreen)
                val mobileNumber:String?=Util_shared_preferences.getMobileNumber(this@SignInScreen)
                val password:String?=Util_shared_preferences.getPassword(this@SignInScreen)
                if(!token!!.isEmpty()){
                    SchoolAPIServices.validateLoginToken(this@SignInScreen, mobileNumber, password, false)
                }
                else{
                    SchoolAPIServices.generateNewLoginToken(this@SignInScreen, mobileNumber, password)
                }

            }

            override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence) {

            }

            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {

            }
        }
        val dialogV23 = BiometricDialogV23(this@SignInScreen, biometricCallback!!)
        BiometricManager(
            this@SignInScreen,
            "Touch ID for 'Voicesnap Messenger'",
            "Use Fingerprint or Password to Login",
            "",
            "Cancel",
            "Error text",
            dialogV23
        ).authenticate(biometricCallback!!)



    }
    private fun setTextStyle() {
        lblNumber?.setTypeface(Typeface.DEFAULT_BOLD)
        lblPassword?.setTypeface(Typeface.DEFAULT_BOLD)
        btnLogin?.setTypeface(Typeface.DEFAULT_BOLD)
    }
    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnLogin -> {
                MobileNumber = txtPhoneNumber.text.toString()
                Password = txtPassword.text.toString()
                loginValidation()
            }
            R.id.lblForgotPassword -> {
                if (txtPhoneNumber.text.toString().length == mobileLength) {
                    SchoolAPIServices.generateOTP(this, "forgot", txtPhoneNumber.text.toString(),this)
                } else {
                    UtilConstants.normalToast(this, "Enter valid mobile number")
                }
            }
            R.id.imgEye -> {
                passwordHideandShow()
            }
        }
    }
    private fun loginValidation(){
        if(MobileNumber?.length == mobileLength){
            if(Password?.isNotEmpty() == true){
                val token:String?=Util_shared_preferences.getLoginToken(this)
                if(token?.isNotEmpty() == true){
                    SchoolAPIServices.validateLoginToken(this, MobileNumber, Password, false)
                }
                else{
                    SchoolAPIServices.generateNewLoginToken(this, MobileNumber, Password)
                }
            }
            else{
                UtilConstants.normalToast(this, "Enter the password")
            }
        }
        else{
            UtilConstants.normalToast(this, "Enter valid mobile number")
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

    override fun callBackValue(otpData: OtpData?) {
        Log.d("callBackvalue",otpData!!.otp_message)
    }
}


