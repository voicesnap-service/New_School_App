package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.content.IntentFilter
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupWindow
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.vsnapnewschool.voicesnapmessenger.Adapters.ForgotDialNumbers
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GenerateOtpCallBack
import com.vsnapnewschool.voicesnapmessenger.Network.APIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.OtpData
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.normalToast
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Utils.AppSignatureHelper
import com.vsnapnewschool.voicesnapmessenger.Utils.MySMSBroadcastReceiver
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import kotlinx.android.synthetic.main.otp_screen.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class OTPScreen : BaseActivity(), View.OnClickListener,
    MySMSBroadcastReceiver.OTPReceiveListener, GenerateOtpCallBack {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    private var smsReceiver: MySMSBroadcastReceiver? = null
    var OTP_REGEX: String? = "[0-9]{1,6}"
    var OTP: String? = ""
    var MobileNumber: String? = ""
    var popupWindow:PopupWindow = PopupWindow()

    lateinit var dialNumbersAdapter: ForgotDialNumbers

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_screen)

        val mLayoutManager = LinearLayoutManager(this)
        recycleNumbers.layoutManager = mLayoutManager
        recycleNumbers.setHasFixedSize(true)
        recycleNumbers.itemAnimator = DefaultItemAnimator()

        enableCrashLytics()
        imgNext.setOnClickListener(this)
        imgBack.setOnClickListener(this)
        lblVerification.setTypeface(Typeface.DEFAULT_BOLD)
        MobileNumber=Util_shared_preferences.getMobileNumber(this@OTPScreen)

        lblMobileNumber.setText(MobileNumber)
        lblMobileNumber.isEnabled=false
        val appSignature = AppSignatureHelper(this)
        Log.v("AppSignature", appSignature.appSignatures.toString())
        startSMSListener()
        imgNext.setOnClickListener(this)
        btnGetOTP.setOnClickListener(this)
        lblResend.setOnClickListener(this)
        imgBack.setOnClickListener(this)
        UtilConstants.textWatcher(this, txtEdit1, txtEdit2, txtEdit3, txtEdit4)
        if(UtilConstants.OTPScreenType.equals("Forgot")) {
            lblVerification.setText("Reset Password")
            lblResend.visibility=View.GONE
            btnGetOTP.visibility=View.GONE
            lblClickHere.visibility=View.GONE
            recycleNumbers.visibility=View.GONE
            lblMissedCallFrom.visibility=View.GONE

        }
        else if(UtilConstants.OTPScreenType.equals("New")){
            lblVerification.setText("Phone verification")
            lblResend.visibility=View.VISIBLE
            btnGetOTP.visibility=View.VISIBLE
        }
    }

    private fun startSMSListener() {
        try {
            smsReceiver = MySMSBroadcastReceiver()
            smsReceiver?.initOTPListener(this)
            val intentFilter = IntentFilter()
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION)
            this.registerReceiver(smsReceiver, intentFilter)
            val client = SmsRetriever.getClient(this)
            val task = client.startSmsRetriever()
            task.addOnSuccessListener {
                // API successfully started
            }
            task.addOnFailureListener {
                // Fail to start API
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onBtnResendClick(view: View) {
        startSMSListener()
    }

    override fun onOTPReceived(messageBody: String) {
        val pattern: Pattern = Pattern.compile(OTP_REGEX)
        val matcher: Matcher = pattern.matcher(messageBody)
        while (matcher.find()) {
            OTP = matcher.group()
        }
        Log.d("OTP", OTP.toString())
        txtEdit1.setText(OTP?.get(0).toString())
        txtEdit2.setText(OTP?.get(1).toString())
        txtEdit3.setText(OTP?.get(2).toString())
        txtEdit4.setText(OTP?.get(3).toString())

        if (smsReceiver != null) {
            //LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver!!)  //command by Murugan
        }
    }
    override fun onOTPTimeOut() {
        // showToast("OTP Time out")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (smsReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(smsReceiver!!)
        }
    }
    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imgNext -> {

                val otp: String? =
                    txtEdit1.text.toString() + txtEdit2.text.toString() + txtEdit3.text.toString() + txtEdit4.text.toString()

                if (!otp!!.isEmpty()) {
                    APIServices.validateOTP(this, MobileNumber, otp)
                } else {
                    normalToast(this, "Enter the OTP")
                }
            }
            R.id.imgBack -> {
                onBackPressed()
            }
            R.id.btnGetOTP -> {
                APIServices.generateOTP(this, "new", MobileNumber, this)
            }
            R.id.lblResend -> {
                APIServices.generateOTP(this, "resend", MobileNumber, this)
            }
        }
    }


    private fun setDialNumbersAdapter(otpData: OtpData?) {
        lblClickHere.visibility=View.VISIBLE
        lblMissedCallFrom.visibility=View.VISIBLE
        recycleNumbers.visibility=View.VISIBLE
        lblClickHere.setText(otpData?.more_info)
        lblMissedCallFrom.setText(otpData?.otp_message)


        val dialNumber: Array<String> = otpData?.dial_numbers!!.split(",".toRegex()).toTypedArray()
        dialNumbersAdapter = ForgotDialNumbers(dialNumber, this@OTPScreen, popupWindow!!)
        val mLayoutManager = LinearLayoutManager(this)
        recycleNumbers.layoutManager = mLayoutManager
        recycleNumbers.setHasFixedSize(true)
        recycleNumbers.itemAnimator = DefaultItemAnimator()
        recycleNumbers.adapter = dialNumbersAdapter
        dialNumbersAdapter!!.notifyDataSetChanged()
    }

    override fun callBackValue(otpData: OtpData?) {

        Log.d("data", otpData!!.dial_numbers)
        setDialNumbersAdapter(otpData)

    }
}