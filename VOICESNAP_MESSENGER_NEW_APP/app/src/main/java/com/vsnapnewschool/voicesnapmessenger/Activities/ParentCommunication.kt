package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentCommunicationAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.parentCommunicationListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*


class ParentCommunication : BaseActivity(),View.OnClickListener {
    internal lateinit var adapterCommunication: ParentCommunicationAdapter
    private val menulist = ArrayList<Text_Class>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Communication))
        enableSearch(true)
        parent_bottom_layout.visibility=View.VISIBLE
        scrollAdds(this,imageSlider)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        ImageLength()
        adapterCommunication = ParentCommunicationAdapter(menulist, this, object : parentCommunicationListener {
            override fun oncommunicationClick(holder: ParentCommunicationAdapter.MyViewHolder, text_info: Text_Class) {
                holder.communication.setOnClickListener({

//                    if(UtilConstants.PARENT_MENU_TYPE == UtilConstants.PARENT_MENU_VOICE){
//                        UtilConstants.parentVoiceCommunicationView(this@ParentCommunication)
//                    }
//                    else if(UtilConstants.PARENT_MENU_TYPE == UtilConstants.PARENT_MENU_TEXT){
//                        UtilConstants.parentTextMessageView(this@ParentCommunication)
//                    }

                })

            }
        })
        val mLayoutManager2 = LinearLayoutManager(this)
        recyclerview.layoutManager = mLayoutManager2
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = adapterCommunication
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })

    }
    private fun ImageLength() {
        var menus = Text_Class("Annual Day 2020", "SN ", " Sent Successfully ", "Sent on Jun 05 at 10.30 am")
        menulist.add(menus)
        menus = Text_Class("Sports Day", "PK", " Sent Successfully ", "Sent on Jun 05 at 10.30 am")
        menulist.add(menus)
        menus = Text_Class("Teachers Day", "UV", " Sent Successfully ", "Sent on Jun 05 at 10.30 am")
        menulist.add(menus)
        menus = Text_Class("Farewell Day", "RT ", " Sent Successfully ", "Sent on Jun 05 at 10.30 am")
        menulist.add(menus)
        menus = Text_Class( "Library Day", "NN", " Sent Successfully ", "Sent on Jun 05 at 10.30 am")
        menulist.add(menus)

    }
    fun filter(s: String) {

        val assignment: ArrayList<Text_Class> = ArrayList<Text_Class>()
        for (d in menulist) {
            val value: String =
                d.status!!.toLowerCase() + d.recipients!!.toLowerCase() + d.content!!.toLowerCase()
            if (value.contains(s.toLowerCase())) {
                assignment.add(d)
            } else if (!value.contains(s) && assignment.size == 0) {
            }
        }
        if (menulist.size != 0)
            adapterCommunication.update(assignment);
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
        }    }
}
