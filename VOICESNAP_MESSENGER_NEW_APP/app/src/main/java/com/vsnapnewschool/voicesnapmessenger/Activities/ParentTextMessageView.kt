package com.vsnapnewschool.voicesnapmessenger.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentCommunicationViewAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentTextMessageAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.Refreshlistener
import com.vsnapnewschool.voicesnapmessenger.Models.Voice_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class ParentTextMessageView : BaseActivity(),View.OnClickListener {

    internal lateinit var parentTextMessageAdapter: ParentTextMessageAdapter
    var country = java.util.ArrayList<String>()
    private val menulist = ArrayList<Voice_Class>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recyclerview_layout)

        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        enableSearch(true)
        setTitle(getString(R.string.title_Text))
        ImageLength()
        parent_bottom_layout.visibility= View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)


        parentTextMessageAdapter = ParentTextMessageAdapter(menulist, this)
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = parentTextMessageAdapter

    }

    private fun ImageLength() {
        var menus = Voice_Class(
            "Kotlin has great support and many contributions from the community, which is growing all over the world",
            " 4233 Recipients ",
            "text",
            "Sent on Jun 05 at 10.30 am",
            0,
            5,
            -1,
            "FILETEST_00"
        )
        menulist.add(menus)
        menus = Voice_Class(
            "Kotlin has great support and many contributions from the community, which is growing all over the world",
            " 4233 Recipients ",
            "text",
            "Sent on Jun 05 at 10.30 am",
            0,6,
            -1,
            "FILETEST_00"
        )
        menulist.add(menus)
        menus = Voice_Class(
            "Kotlin has great support and many contributions from the community, which is growing all over the world",
            " 4233 Recipients ",
            "text",
            "Sent on Jun 05 at 10.30 am",
            0,7,
            -1,
            "FILETEST_00"
        )
        menulist.add(menus)
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