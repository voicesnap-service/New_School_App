//package com.vsnapnewschool.voicesnapmessenger.Utils
//
//import android.content.Intent
//import android.net.Uri
//import android.view.KeyEvent
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.core.content.ContextCompat.startActivity
//import com.vsnapnewschool.voicesnapmessenger.R
//
//class HelloWebViewClient : WebViewClient() {
//
//    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//
//
//        if (Uri.parse(url).host == getString(R.string.website_domain)) {
//            return false
//        }
//        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//        startActivity(intent)
//        return true
//    }
//
//    override fun onPageFinished(view: WebView, url: String) {
//        // TODO Auto-generated method stub
//        super.onPageFinished(view, url)
//    }
//
//}
//
//override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
//    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
//        webview.goBack()
//        return true
//    }
//    return super.onKeyDown(keyCode, event)
//}