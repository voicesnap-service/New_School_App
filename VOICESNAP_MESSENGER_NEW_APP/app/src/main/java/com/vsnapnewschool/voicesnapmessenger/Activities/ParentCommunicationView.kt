package com.vsnapnewschool.voicesnapmessenger.Activities

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentCommunicationViewAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.Refreshlistener
import com.vsnapnewschool.voicesnapmessenger.Models.Voice_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentCommunicationView : BaseActivity(),View.OnClickListener {
    internal lateinit var communicationViewAdapter: ParentCommunicationViewAdapter
    var country = java.util.ArrayList<String>()
    private val menulist = ArrayList<Voice_Class>()
    private var mediaPlayer = MediaPlayer()
    private val VOICE_FOLDER: String = "School Voice/Voice"
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        enableSearch(true)
        setTitle(getString(R.string.title_Communication))
        ImageLength()
        parent_bottom_layout.visibility=View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)



        communicationViewAdapter = ParentCommunicationViewAdapter(menulist, this,mediaPlayer,object : Refreshlistener{
            override fun refresh() {
                recyclerview.adapter = communicationViewAdapter
            }

        })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = communicationViewAdapter
    }

    private fun ImageLength() {


        var menus = Voice_Class(
//            "https://filesamples.com/samples/audio/mp3/sample1.mp3",
            "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
            " 4233 Recipients ",
            "voice",
            "Sent on Jun 05 at 10.30 am",
            0,
            0,
            -1,
            "FILETEST_01"
        )
        menulist.add(menus)

        menus = Voice_Class(
            "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
            " 4233 Recipients ",
            "voice",
            "Sent on Jun 05 at 10.30 am",
            0,
            1,
            -1,
            "FILETEST_02"
        )
        menulist.add(menus)

        menus = Voice_Class(
            "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
            " 4233 Recipients ",
            "voice",
            "Sent on Jun 05 at 10.30 am",
            0,
            2,
            -1,
            "FILETEST_01"
        )
        menulist.add(menus)

        menus = Voice_Class(
            "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3",
            " 4233 Recipients ",
            "voice",
            "Sent on Jun 05 at 10.30 am",
            0,
            3,
            -1,
            "FILETEST_02"
        )
        menulist.add(menus)

        menus = Voice_Class(
            "https://file-examples-com.github.io/uploads/2017/11/file_example_MP3_700KB.mp3",
            " 4233 Recipients ",
            "voice",
            "Sent on Jun 05 at 10.30 am",
            0,
            4,
            -1,
            "FILETEST_01"
        )
        menulist.add(menus)







    }
    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
    override fun onBackPressed() {
        if(mediaPlayer.isPlaying){
            mediaPlayer.stop()
        }
        super.onBackPressed()
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
