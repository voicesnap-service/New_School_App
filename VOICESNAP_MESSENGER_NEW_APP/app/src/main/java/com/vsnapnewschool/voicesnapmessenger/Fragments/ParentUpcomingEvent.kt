package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentEventsAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.UpcomingEventsCallback
import com.vsnapnewschool.voicesnapmessenger.Interfaces.eventsparentListener
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices.getUpcomingEventsList
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.EventsData
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.UpcomingEventsResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants

import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.ArrayList

class ParentUpcomingEvent : Fragment(), UpcomingEventsCallback {

    internal lateinit var upcomingEventAdapter: ParentEventsAdapter
    var upcomingEventsList = ArrayList<EventsData>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        getUpcomingEventsList(activity!!,this)


//        (activity as BaseActivity?)!!.searchView!!.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener, android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                filter(newText)
//
//                return false
//            }
//
//
//        })
    }



    override fun callbackUpcomingEvents(responseBody: UpcomingEventsResponse) {

        upcomingEventsList.clear()
        upcomingEventsList= responseBody.data as ArrayList<EventsData>
        
        upcomingEventAdapter = ParentEventsAdapter(upcomingEventsList,activity!!,true, object :
            eventsparentListener {
            override fun oneventClick(holder: ParentEventsAdapter.MyViewHolder, text_info: EventsData) {
                holder.CircularLayout.setOnClickListener({
                    UtilConstants.parentEventsHistoryActivity(activity,text_info)
                })
            }
        })

        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = upcomingEventAdapter
    }



//
//    fun filter(s: String) {
//        val assignment: ArrayList<EventsImageClass> = ArrayList<EventsImageClass>()
//        for (d in menulist) {
//            val value: String =
//                d.Day!!.toLowerCase() + d.Content!!.toLowerCase() + d.description!!.toLowerCase()
//            if (value.contains(s.toLowerCase())) {
//                assignment.add(d)
////                lblNoRecordsFound.setVisibility(View.GONE)
//            } else if (!value.contains(s) && assignment.size == 0) {
////                lblNoRecordsFound.setVisibility(View.VISIBLE)
//            }
//        }
//        if (menulist.size != 0)
//            assignmentDueAdapter.update(assignment);
//    }

}
