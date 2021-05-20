package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.webkit.*
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.video_play.*

class ParentVideoView : BaseActivity(),View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.video_play)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Video))
        enableSearch(false)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        val VideoID = intent.getStringExtra("item")
        Log.d("Videoidframe", VideoID.toString())
        mywebview.setBackgroundColor(resources.getColor(R.color.clr_black))
        mywebview.getSettings().setJavaScriptEnabled(true)
        mywebview.getSettings().setAppCacheEnabled(true)
        mywebview.getSettings().setDomStorageEnabled(true)
        mywebview.getSettings().setSupportZoom(false)
        mywebview.getSettings().setBuiltInZoomControls(false)
        mywebview.getSettings().setDisplayZoomControls(false)
        mywebview.setScrollContainer(false)
        mywebview.setHorizontalScrollBarEnabled(false)
        mywebview.setVerticalScrollBarEnabled(false)

        mywebview.setOnTouchListener(OnTouchListener { v, event -> event.action == MotionEvent.ACTION_MOVE })
        mywebview.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                webView: WebView,
                request: WebResourceRequest
            ): Boolean {

                return true
            }
        })
        mywebview.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                setProgress(progress * 100)
                if (progress == 100) {
                }
            }
        })
        mywebview.getSettings().setPluginState(WebSettings.PluginState.ON)
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels



        val data_html = "<!DOCTYPE html><html> " +
                "<head>" +
                " <meta charset=\"UTF-8\">" +
                "</head>" +
                " <body style=\"background:black;margin:0 0 0 0; padding:0 0 0 0;\"> " +
                "<div class=\"vimeo\">" +
                "<iframe  style=\"position:absolute;top:0;bottom:0;width:100%;height:100%\" src=\"" + VideoID + "\" frameborder=\"0\">" +
                "</iframe>" +
                " </div>" +
                " </body>" +
                " </html> "
        Log.d("datahtmlweb", data_html)

        mywebview.loadData(data_html, "text/html", "UTF-8")
        Log.d("load", data_html)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgchat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(this)
            }
            R.id.imgSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }

}
