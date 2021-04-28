package com.vsnapnewschool.voicesnapmessenger.Utils

import android.app.Activity
import android.app.AlertDialog
import android.webkit.WebView
import android.webkit.WebViewClient
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import okhttp3.internal.Util

class MyWebViewClient(var context: Activity) : WebViewClient() {
    override fun onReceivedError(view: WebView, errorCode: Int, description: String, failingUrl: String) {
        if (errorCode == ERROR_TIMEOUT) {
            view.stopLoading()
        }
        view.loadData(
            "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"></head>" +
                    "<body><a href=\"" + view.url + "\">Turn on your network connection and click here</a></body></html>",
            "text/html",
            "utf-8")
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if (DownloadVoice.isNetworkAvailable(context)) {
            view.loadUrl(url)
        } else {
            showAlert("Connectivity", "Check Internet Connection")
        }
        return true
    }

    private fun showAlert(title: String, msg: String) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(msg)
        alertDialog.setNeutralButton("OK") { dialog, which -> }
        alertDialog.show()
    }

}