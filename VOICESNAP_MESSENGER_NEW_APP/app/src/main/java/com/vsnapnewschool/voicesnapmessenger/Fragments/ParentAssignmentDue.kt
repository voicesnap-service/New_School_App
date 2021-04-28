package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentAssignmentDueAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.assignmentDueListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*


class ParentAssignmentDue : Fragment() {
    private val menulist = ArrayList<EventsImageClass>()

    var type:Boolean=true
    internal lateinit var assignmentDueAdapter: ParentAssignmentDueAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    { return inflater.inflate(R.layout.recyclerview_layout, container, false) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ImageLength()

        assignmentDueAdapter = ParentAssignmentDueAdapter(menulist, activity, type, object : assignmentDueListener {
            override fun onassignmentClick(holder: ParentAssignmentDueAdapter.MyViewHolder, item: EventsImageClass) {
                holder.rytDetails1.setOnClickListener({
                    if (type) {
                        UtilConstants.parentAssignmentView(context as Activity, item)
                    }

                })
            }
        })
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = assignmentDueAdapter


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
        var movieModel = EventsImageClass(R.drawable.event1,"Record Submission","10 Dec","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)
        movieModel = EventsImageClass(R.drawable.event2,"Test 123","09 Oct","sportsday 2020","Main Audiotorium","text")
        menulist.add(movieModel)
        movieModel = EventsImageClass( R.drawable.event3,"Submission","08 Nov","freshersday 2020","Main Audiotorium","pdf")
        menulist.add(movieModel)
        movieModel = EventsImageClass( R.drawable.event4,"Record","07 Sep","maths","Main Audiotorium","pdf")
        menulist.add(movieModel)
    }
    fun filter(s: String) {
        val assignment: ArrayList<EventsImageClass> = ArrayList<EventsImageClass>()
        for (d in menulist) {
            val value: String = d.Day!!.toLowerCase() + d.Content!!.toLowerCase() + d.description!!.toLowerCase()
            if (value.contains(s.toLowerCase())) {
                assignment.add(d)
//                lblNoRecordsFound.setVisibility(View.GONE)
            } else if (!value.contains(s) && assignment.size == 0) {
//                lblNoRecordsFound.setVisibility(View.VISIBLE)
            }
        }
        if (menulist.size != 0)
            assignmentDueAdapter.update(assignment);
    }


}
