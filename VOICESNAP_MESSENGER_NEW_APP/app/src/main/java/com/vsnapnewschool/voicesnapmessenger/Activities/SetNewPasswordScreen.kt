package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.login_screen.*
import kotlinx.android.synthetic.main.otp_screen.imgBack
import kotlinx.android.synthetic.main.password_screen.*
import kotlinx.android.synthetic.main.set_new_password_screen.*
import kotlinx.android.synthetic.main.set_new_password_screen.lblNumber
import kotlinx.android.synthetic.main.set_new_password_screen.lblPassword


class SetNewPasswordScreen : BaseActivity(), View.OnClickListener {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }
    var passwordHide: Boolean?=true
    var MobileNumber:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.set_new_password_screen)
        enableCrashLytics()

        setFontStyle()
        btnConfirmPassword.setOnClickListener(this)
        imgBack.setOnClickListener(this)
        imgEyeNew.setOnClickListener(this)
        imgEyeConfirm.setOnClickListener(this)
        MobileNumber=Util_shared_preferences.getMobileNumber(this@SetNewPasswordScreen)
        Util_shared_preferences.putForgotPassword(this@SetNewPasswordScreen,false)

    }
    private fun setFontStyle() {
        lblChange.setTypeface(Typeface.DEFAULT_BOLD)
        lblNumber.setTypeface(Typeface.DEFAULT_BOLD)
        lblPassword.setTypeface(Typeface.DEFAULT_BOLD)
        btnConfirmPassword.setTypeface(Typeface.DEFAULT_BOLD)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnConfirmPassword -> {
                Util_shared_preferences.putPasswordScreen(this,true)
                if(txtNewPassword?.text.toString().equals(txtConfirmpassword?.text.toString())){
                    SchoolAPIServices.setNewPassword(this,txtConfirmpassword?.text.toString(),MobileNumber)
                }
                else{
                    UtilConstants.normalToast(this,"Enter the correct password")
                }

            }
            R.id.imgBack -> {
                onBackPressed()
            }
            R.id.imgEyeNew ->{
                passwordHideandShow(txtNewPassword,imgEyeNew)
            }
            R.id.imgEyeConfirm ->{
                passwordHideandShow(txtConfirmpassword,imgEyeConfirm)
            }
        }
    }

    private fun passwordHideandShow(txtPassword: EditText?,imgEye: ImageView?) {
        Log.d("image","image")
        if(passwordHide == true){
            txtPassword?.transformationMethod = HideReturnsTransformationMethod.getInstance()
            txtPassword?.setSelection(txtPassword?.text.length)
            passwordHide = false
            imgEye?.setImageResource(R.drawable.eye_visibility)
        }
        else{
            txtPassword?.transformationMethod = PasswordTransformationMethod.getInstance()
            txtPassword?.setSelection(txtPassword?.text.length)
            passwordHide = true
            imgEye?.setImageResource(R.drawable.eye_invisibility)

        }
    }
}