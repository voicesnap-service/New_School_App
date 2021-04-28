package com.vsnapnewschool.voicesnapmessenger.Activities

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.*
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.ActivityResult
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.IMMEDIATE
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.CountryListAdapter
import com.vsnapnewschool.voicesnapmessenger.Network.APIServices
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import com.vsnapnewschool.voicesnapmessenger.Utils.CommonClass
import com.vsnapnewschool.voicesnapmessenger.Utils.MyWebViewClient
import kotlinx.android.synthetic.main.activity_countrylist.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.activity_terms_and_conditions.*
import kotlinx.android.synthetic.main.splash_screen.*
import kotlinx.android.synthetic.main.timetable.*
import kotlinx.android.synthetic.main.view_file_imagepdf.*
import retrofit2.Call
import retrofit2.Response
import java.util.*


@Suppress("DEPRECATION")
class SplashScreen : BaseActivity(), CountryListAdapter.OnItemClickListener {

    private var permissionsRequired = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_WIFI_STATE,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CALL_PHONE,
        Manifest.permission.USE_BIOMETRIC,
        Manifest.permission.USE_FINGERPRINT
    )
    private val PERMISSION_CALLBACK_CONSTANT = 100
    private val REQUEST_PERMISSION_SETTING = 101
    private var permissionStatus: SharedPreferences? = null
    private var sentToSettings: Boolean? = false
    var updateRequestCode: Int = 1
    var termsandconditionPopup: PopupWindow? = null
    val termsConditionUrl = "https://gradit.voicesnap.com/School/SchoolTermsConditions"
    private var appUpdateManager: AppUpdateManager? = null
    private var countryList = ArrayList<CountryData>()
    internal lateinit var countryAdapter: CountryListAdapter
    private var countryData: CountryData? = null
    private var popupWindow: PopupWindow? = null
    private var countrySelected: Boolean? = false

    var MobileNumber:String?=""
    var Password:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        enableCrashLytics()

        if (CommonClass.isNetworkAvailable(this)) {
            permissionStatus = getSharedPreferences(getString(R.string.permission_status), Context.MODE_PRIVATE)
            requestPermission()
        } else {
            val rootView: View = window.decorView.findViewById(android.R.id.content)
            val snackbar = Snackbar.make(
                rootView,
                R.string.check_internet,
                Snackbar.LENGTH_INDEFINITE
            )
            snackbar.setAction("RETRY") { view: View? ->
                finishAffinity()
                startActivity(getIntent())
            }
            snackbar.show()
        }
        appUpdateManager = AppUpdateManagerFactory.create(this)
        lblLogin.setOnClickListener {
            UtilConstants.openMobileNumberScreen(this)
        }
         MobileNumber=Util_shared_preferences.getMobileNumber(this@SplashScreen)
         Password=Util_shared_preferences.getPassword(this@SplashScreen)
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager?.unregisterListener(listener)
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager?.appUpdateInfo?.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                appUpdateManager?.startUpdateFlowForResult(
                    appUpdateInfo,
                    IMMEDIATE,
                    this,
                    updateRequestCode
                )
            }
        }
    }

    private fun checkUpdate(updateType: Boolean?) {
        appUpdateManager?.registerListener(listener)
        val appUpdateInfoTask = appUpdateManager?.appUpdateInfo
        appUpdateInfoTask?.addOnSuccessListener { appUpdateInfo ->
            if (updateType!!) {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    appUpdateManager?.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        updateRequestCode
                    )
                    Log.d("Update", "Fource Update")
                }
            } else {
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {
                    appUpdateManager?.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.FLEXIBLE,
                        this,
                        updateRequestCode
                    )
                    Log.d("Update", "Update available")
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == updateRequestCode) {
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.d("TAG", "" + "Result Ok")

                }
                Activity.RESULT_CANCELED -> {
                    Log.d("TAG", "" + "Result Cancelled")
                    //  handle user's rejection  }
                }
                ActivityResult.RESULT_IN_APP_UPDATE_FAILED -> {
                    //if you want to request the update again just call checkUpdate()
                    checkUpdate(true)
                    Log.d("TAG", "" + "Update Failure")
                }
            }
        }
    }

    private val listener: InstallStateUpdatedListener? =
        InstallStateUpdatedListener { installState ->
            if (installState.installStatus() == InstallStatus.DOWNLOADED) {
                val rootView: View = window.decorView.findViewById(android.R.id.content)
                val snackbar = Snackbar.make(
                    rootView,
                    "An update has just been downloaded.",
                    Snackbar.LENGTH_INDEFINITE
                )
                snackbar.setAction("RESTART") { view: View? ->
                    appUpdateManager?.completeUpdate()
                }
                snackbar.show()

            }
        }

    private fun requestPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[0]
            ) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[1]
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[2]
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[3]
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[4]
            ) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[5]
            )
            != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[6]
            )
            != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[7]
            )
            != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[8]
            )
            != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(
                this,
                permissionsRequired[9]
            )
            != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[4])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[5])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[6])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[7])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[8])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[9])) {
                    getAlertDialog()

            }
            else if (permissionStatus!!.getBoolean(permissionsRequired[0], false)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Need Permissions")
                builder.setMessage("This app needs permissions.")
                builder.setPositiveButton("Grant") { dialog, which ->
                    dialog.cancel()
                    sentToSettings = true
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, REQUEST_PERMISSION_SETTING)
                    Toast.makeText(
                        applicationContext,
                        "Go to Permissions to Grant ",
                        Toast.LENGTH_LONG
                    ).show()
                }
                builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
                builder.show()
            }
            else {
                ActivityCompat.requestPermissions(
                    this,
                    permissionsRequired,
                    PERMISSION_CALLBACK_CONSTANT
                )
            }
            val editor = permissionStatus!!.edit()
            //changed by me true to false
            editor.putBoolean(permissionsRequired[0], false)
            editor.commit()
        } else {
            Handler().postDelayed({
                openLaunchScreen()

            }, 100)
        }
    }

    private fun openLaunchScreen() {
        if (Util_shared_preferences.getExistingUser(this) == true) {
            val ForgotPassword:Boolean?=Util_shared_preferences.getForgotPassword(this)
            if(ForgotPassword == true){
                APIServices.checkMobileNumber(this)
            }
            else {
                appVersioncheck()
            }
        }
        else {
            popTermsAndCondition()
        }
    }
    private fun popTermsAndCondition() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupview: View = inflater.inflate(R.layout.activity_terms_and_conditions, null)
        val popupWindow = PopupWindow(
            popupview,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        popupWindow.setContentView(popupview)
        popupWindow.animationStyle = R.style.AnimationPopup_fade
        popupWindow.showAtLocation(ConstParent, Gravity.CENTER, 0, 0)


        val webView = popupview.findViewById<WebView>(R.id.webtermsView)
        val btnterms: Button = popupview.findViewById<Button>(R.id.btnAgreeTermsandCondition)
        val container = popupWindow.getContentView().getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.8f
        wm.updateViewLayout(container, p)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)
        webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                progressDialog.show()
                setProgress(progress * 100)
                if (progress == 100) {
                    progressDialog.dismiss()
                }
            }
        })

        webView.setWebViewClient(MyWebViewClient(this))
        webView.getSettings().setLoadsImagesAutomatically(true)
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)
        val webSettings: WebSettings = webView.getSettings()
        webView.getSettings().setBuiltInZoomControls(true)
        webSettings.javaScriptEnabled = true
        webView.loadUrl(termsConditionUrl)
        btnterms.setOnClickListener {
            popupWindow.dismiss()
            if (!popupWindow.isShowing) {
                agreeTermsAndConditions()
            }
        }
    }

    private fun agreeTermsAndConditions() {

        val secureID: String = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        val jsonObject = JsonObject()
        jsonObject.addProperty("secure_id", secureID)
        jsonObject.addProperty("other_details", "Android")
        jsonObject.addProperty("is_agreed", 1)
        Log.d("Request", jsonObject.toString())
        GifLoading.loading(this, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
            apiInterface.agreeTermsandConditions(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StatusMessageResponse?> {
                override fun onResponse(
                    call: Call<StatusMessageResponse?>?, response: Response<StatusMessageResponse?>?) {
                    try {
                        GifLoading.loading(this@SplashScreen, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("AgreeTermsResponse", gson.toJson(response))

                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                showCountryListPopup()

                            } else {
                                UtilConstants.normalToast(this@SplashScreen, responseBody?.message)
                            }
                        }
                        else{
                          finish()
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<StatusMessageResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    GifLoading.loading(this@SplashScreen, false)

                    UtilConstants.normalToast(this@SplashScreen, t.toString())
                }
            })
    }

    private fun showCountryListPopup() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val Countrypopupview: View = inflater.inflate(R.layout.activity_countrylist, null)
        val popupWindow = PopupWindow(
            Countrypopupview,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT,
            true
        )
        popupWindow.setContentView(Countrypopupview)
        popupWindow.animationStyle = R.style.AnimationPopup
        popupWindow.showAtLocation(Countrypopupview, Gravity.CENTER, 0, 0)

        val recycleCountry: RecyclerView = Countrypopupview.findViewById<RecyclerView>(R.id.recycleCountry)
        val lblCancel: TextView = Countrypopupview.findViewById<TextView>(R.id.lblCancel)
        val lblOk: TextView = Countrypopupview.findViewById<TextView>(R.id.lblOk)
        val container = popupWindow.getContentView().getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.8f
        wm.updateViewLayout(container, p)
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading")
        progressDialog.setCancelable(false)

        getCountryList(recycleCountry)
        lblCancel.setOnClickListener(View.OnClickListener {
            popupWindow.dismiss()
            finishAffinity()
        })
        lblOk.setOnClickListener(View.OnClickListener {
            if (countrySelected!!) {
                Util_shared_preferences.putCountryNameandUrl(this, countryData!!)
                Util_shared_preferences.putCountryInformation(this, countryData!!)
                Util_shared_preferences.putExistingUser(this, true)
                popupWindow.dismiss()
                welcomeBg.visibility = View.VISIBLE
            } else {
                UtilConstants.normalToast(this, "Please choose your country")
            }
        })
    }

    private fun getCountryList(recycleCountry: RecyclerView) {
        GifLoading.loading(this, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getCountryList()!!
            .enqueue(object : retrofit2.Callback<CountryListResponse?> {
                override fun onResponse(
                    call: Call<CountryListResponse?>?,
                    response: Response<CountryListResponse?>?) {
                    try {
                        GifLoading.loading(this@SplashScreen, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("CountryListResponse", gson.toJson(response))

                        if(response?.code()==200) {
                            if (responseBody?.status == 1) {
                                countryList = responseBody.data as ArrayList<CountryData>
                                countryAdapter = CountryListAdapter(
                                    countryList,
                                    this@SplashScreen,
                                    this@SplashScreen
                                )
                                val mLayoutManager = LinearLayoutManager(this@SplashScreen)
                                recycleCountry.layoutManager = mLayoutManager
                                recycleCountry.itemAnimator = DefaultItemAnimator()
                                recycleCountry.adapter = countryAdapter
                                countryAdapter.notifyDataSetChanged()

                            } else {
                                UtilConstants.customFailureAlert(
                                    this@SplashScreen,
                                    responseBody?.message
                                )
                            }
                        }
                        else if(response?.code()==400 || response?.code()==500) {
                            val errorResponseBody = Gson().fromJson(response.errorBody()?.charStream(), StatusMessageResponse::class.java)
                            UtilConstants.handleErrorResponse(this@SplashScreen,response.code(),errorResponseBody)
                        }
                        else{
                            UtilConstants.normalToast(this@SplashScreen, this@SplashScreen.getString(R.string.Service_unavailable))
                        }

                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<CountryListResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    GifLoading.loading(this@SplashScreen, false)
                    UtilConstants.normalToast(this@SplashScreen, t.toString())
                }
            })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {
            //check if all permissions are granted
            var allgranted = false
            for (i in grantResults.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true
                } else {
                    allgranted = false
                    break
                }
            }
            if (allgranted) {
                Handler().postDelayed({
                    openLaunchScreen()
                }, 100)

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    permissionsRequired[0]
                )
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[1])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[2])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[3])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[4])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[5])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[6])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[7])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[8])
                || ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[9])
            ) {


                getAlertDialog()


            } else {
                Toast.makeText(applicationContext, "Unable to get Permission", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun appVersioncheck() {
        var CountryID: Int? = Util_shared_preferences.getCountryID(this)
        val jsonObject = JsonObject()
        jsonObject.addProperty("version_code", "3")
        jsonObject.addProperty("app_id", "3")
        jsonObject.addProperty("device_type", "Android")
        jsonObject.addProperty("country_id", CountryID)
        Log.d("Request",jsonObject.toString())
        GifLoading.loading(this, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.VersionCheck(jsonObject)!!
            .enqueue(object : retrofit2.Callback<VersionCheckResponse?> {
                override fun onResponse(
                    call: Call<VersionCheckResponse?>?,
                    response: Response<VersionCheckResponse?>?
                ) {
                    try {
                        GifLoading.loading(this@SplashScreen, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("VersionCheckResponse", gson.toJson(response))

                        if(response?.code()==200) {
                            if (responseBody?.status == 1) {
                                val UpdateAvailable: Int = responseBody.data[0].UpdateAvailable
                                val ForceUpdate: Int = responseBody.data[0].ForceUpdate
                                val isAlertAvailable: Int = responseBody.data[0].isAlertAvailable

                                if (UpdateAvailable == 0 && ForceUpdate == 0) {
                                    if (isAlertAvailable == 1) {
                                        showWhatsNewAlert(responseBody.data[0].VersionAlertTitle, responseBody.data[0].VersionAlertContent)
                                    } else {
                                        checkAutoLogin()
                                    }
                                } else if (UpdateAvailable == 1 && ForceUpdate == 0) {
                                    checkUpdate(false)

                                } else if (UpdateAvailable == 1 && ForceUpdate == 1) {
                                    checkUpdate(true)
                                }

                            }
                            else {
                                UtilConstants.normalToast(this@SplashScreen, responseBody?.message)
                            }
                        }
                        else{
                            finish()
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<VersionCheckResponse?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    GifLoading.loading(this@SplashScreen, false)
                    UtilConstants.normalToast(this@SplashScreen, t.toString())
                    finishAffinity()
                }
            })
    }

    private fun checkAutoLogin() {
        val passwordScreen:Boolean?=Util_shared_preferences.getPasswordScreen(this@SplashScreen)
        if(passwordScreen == true){
            val userAlreadyLoggedIn :Boolean?=Util_shared_preferences.getUserLoggedIn(this@SplashScreen)
            if(userAlreadyLoggedIn == true)
            {
                val token:String?=Util_shared_preferences.getLoginToken(this@SplashScreen)
                if(token?.isNotEmpty() == true){
                    APIServices.validateLoginToken(this@SplashScreen,MobileNumber,Password,true)
                }
                else{
                    APIServices.generateNewLoginToken(this@SplashScreen,MobileNumber,Password)
                }
            } else {
                UtilConstants.openSignInScreen(this@SplashScreen)
            }
        }
        else{
            UtilConstants.openMobileNumberScreen(this@SplashScreen)
        }
    }

    private fun showWhatsNewAlert(title: String?, content: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.whats_new_update)
        val lblOk = dialog.findViewById<TextView>(R.id.lblOk)
        val lblWhatsNewContent = dialog.findViewById<TextView>(R.id.lblWhatsNewContent)
        val lblTitle = dialog.findViewById<TextView>(R.id.lblTitle)
        lblTitle!!.setText(title)
        lblWhatsNewContent!!.setText(content)
        dialog.setCancelable(false)
        lblOk.setOnClickListener(View.OnClickListener {
            checkAutoLogin()
        })
        dialog.show()

    }

    private fun getAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Need Multiple Permissions")
        builder.setMessage("This app needs permissions.")
        builder.setPositiveButton("Grant") { dialog, which ->
            dialog.cancel()
            ActivityCompat.requestPermissions(
                this,
                permissionsRequired,
                PERMISSION_CALLBACK_CONSTANT
            )
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }
        builder.show()
    }

    override fun onPostResume() {
        super.onPostResume()
        if (sentToSettings!!) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permissionsRequired[0]
                ) == PackageManager.PERMISSION_GRANTED
            )
                openLaunchScreen()
        }
    }

    override fun onItemClick(view: View, countryDatas: CountryData) {
        countryData = countryDatas
        countrySelected = true
    }

    override fun onRemoveClick(view: View, countryDatas: CountryData) {
        countrySelected = false
    }
}




