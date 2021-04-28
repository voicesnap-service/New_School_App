package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.vsnapnewschool.voicesnapmessenger.CallBacks.ReturnGlobalValue
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_IMPORTANT_INFO
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ONLINE_TEXT_BOOK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_OTHER_SERVICES
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.activity_webview.*

class OfferNewProductOnlineBook: BaseActivity(), ReturnGlobalValue {
    var MobileNumber:String?=null
    var WebViewLink:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        enableCrashLytics()
        initializeActionBar()

        MobileNumber= Util_shared_preferences.getMobileNumber(this)
        if(MENU_TYPE == MENU_IMPORTANT_INFO ){
            setTitle("Important Info")
            SchoolAPIServices.getGlobalValues(this, "offers_link", this)
        }
        else if(MENU_TYPE == MENU_ONLINE_TEXT_BOOK){
            setTitle("Online Text Book")
            SchoolAPIServices.getGlobalValues(this, "online_textbooklink", this)
        }
        else if(MENU_TYPE == MENU_OTHER_SERVICES){
            setTitle("Other Services")
            SchoolAPIServices.getGlobalValues(this, "new_product_link", this)
        }

    }
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack())
            webView.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }

    override fun callBackValue(value: String?) {
        loadWebWiew(value!!)

    }
    private fun loadWebWiew(value: String) {

        if(MENU_TYPE == MENU_IMPORTANT_INFO ){
            WebViewLink=value+MobileNumber
        }
        else if(MENU_TYPE == MENU_ONLINE_TEXT_BOOK){
            WebViewLink=value
        }
        else if(MENU_TYPE == MENU_OTHER_SERVICES){
            WebViewLink=value+MobileNumber
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                GifLoading.loading(this@OfferNewProductOnlineBook,true)
                if (progress == 100) {
                    GifLoading.loading(this@OfferNewProductOnlineBook,false)
                }
            }
        }
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(WebViewLink!!)
    }
}



