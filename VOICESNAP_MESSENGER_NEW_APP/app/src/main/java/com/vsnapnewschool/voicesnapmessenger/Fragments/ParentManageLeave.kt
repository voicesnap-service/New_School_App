package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Adapters.ManageLeaveAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.Leave_Class
import com.vsnapnewschool.voicesnapmessenger.R
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*


class ParentManageLeave: Fragment() {
    private val menulist = ArrayList<Leave_Class>()
    internal lateinit var adapterl: ManageLeaveAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {return inflater.inflate(
        R.layout.recyclerview_layout, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adds_layout.visibility=View.GONE

        ImageLength()
        adapterl = ManageLeaveAdapter(menulist, context)
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recyclerview.adapter = adapterl


        (activity as BaseActivity?)!!.searchView!!.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
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
        var menus = Leave_Class("Approved", "yesterday", "02 Dec 2020", "03 Dec 2020", "fever", "sick leave")
        menulist.add(menus)
        menus = Leave_Class("pending", "yesterday", "02 Dec 2020", "03 Dec 2020", "fever", "sick leave")
        menulist.add(menus)
        menus = Leave_Class("pending", "yesterday", "02 Dec 2020", "03 Dec 2020", "fever", "sick leave")
        menulist.add(menus)
        menus = Leave_Class("Rejected", "yesterday", "02 Dec 2020", "03 Dec 2020", "fever", "sick leave")
        menulist.add(menus)
        menus = Leave_Class( "pending", "yesterday", "02 Dec 2020", "03 Dec 2020", "fever", "sick leave")
        menulist.add(menus)

    }

    fun filter(s: String) {

        val assignment: ArrayList<Leave_Class> = ArrayList<Leave_Class>()
        for (d in menulist) {
            val value: String =
                d.status!!.toLowerCase() + d.lblstartdate!!.toLowerCase() + d.leavetype!!.toLowerCase() + d.leavereason!!.toLowerCase()
            if (value.contains(s.toLowerCase())) {
                assignment.add(d)
                lblNoRecordsFound.setVisibility(View.GONE)
            } else if (!value.contains(s) && assignment.size == 0) {
                lblNoRecordsFound.setVisibility(View.VISIBLE)
            }
        }
        if (menulist.size != 0)
            adapterl.update(assignment);
    }
}