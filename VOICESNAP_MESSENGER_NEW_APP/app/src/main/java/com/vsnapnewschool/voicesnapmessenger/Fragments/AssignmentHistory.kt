package com.vsnapnewschool.voicesnapmessenger.Fragments


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Adapters.AssignmentHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_history.*
import java.util.*

class Assignment_HistoryFragment : Fragment() {
    private val menulist = ArrayList<EventsImageClass>()
    internal lateinit var assignmentHistoryAdapter: AssignmentHistoryAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ImageLength()
        assignmentHistoryAdapter = AssignmentHistoryAdapter(
            menulist,
            context,
            object : AssignmentHistoryAdapter.layoutClickListener {
                override fun onBtnClick(holder: AssignmentHistoryAdapter.MyViewHolder, text_info: EventsImageClass) {
                    holder.rytDetails1.setOnClickListener({
                        UtilConstants.teacherassignmentHistoryActivity(context as Activity,text_info)
                    })

                }
            })
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerHistory.layoutManager = mLayoutManager
        recyclerHistory.itemAnimator = DefaultItemAnimator()
        recyclerHistory.adapter = assignmentHistoryAdapter

        (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
    }

    private fun ImageLength() {
        var movieModel = EventsImageClass(R.drawable.event1,"Record Submission","10 Dec","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)
        movieModel = EventsImageClass(R.drawable.event2,"Record Submission","09 Oct","AnnualDay 2020","Main Audiotorium","text")
        menulist.add(movieModel)
        movieModel = EventsImageClass( R.drawable.event3,"Record Submission","08 Nov","AnnualDay 2020","Main Audiotorium","pdf")
        menulist.add(movieModel)
        movieModel = EventsImageClass( R.drawable.event4,"Record Submission","07 Sep","maths","Main Audiotorium","pdf")
        menulist.add(movieModel)
    }
}
