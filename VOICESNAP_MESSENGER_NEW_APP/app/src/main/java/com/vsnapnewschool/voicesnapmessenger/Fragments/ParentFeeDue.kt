package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.vsnapnewschool.voicesnapmessenger.Adapters.CustomExpandableListAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.ExpandableListData.data
import com.vsnapnewschool.voicesnapmessenger.R
import kotlinx.android.synthetic.main.parent_activity_fees_detail.*
import java.util.*

class ParentFeeDue : Fragment() {
    private var adapter: ExpandableListAdapter? = null
    private var titleList: List<String>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.parent_activity_fees_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        if (expandableListView != null) {
            val listData = data
            titleList = ArrayList(listData.keys)
            adapter = activity?.let { CustomExpandableListAdapter(it, titleList as ArrayList<String>, listData) }
            expandableListView!!.setAdapter(adapter)
            expandableListView!!.setOnGroupExpandListener { groupPosition ->
                Toast.makeText(context,
                    (titleList as ArrayList<String>)[groupPosition] + " List Expanded.", Toast.LENGTH_SHORT).show()
            }
            expandableListView!!.setOnGroupCollapseListener { groupPosition ->
                Toast.makeText(
                    context,
                    (titleList as ArrayList<String>)[groupPosition] + " List Collapsed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            expandableListView!!.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
                Toast.makeText(
                    context,
                    "Clicked: " + (titleList as ArrayList<String>)[groupPosition] + " -> " + listData[(
                            titleList as
                                    ArrayList<String>
                            )
                            [groupPosition]]!!.get(
                        childPosition
                    ),
                    Toast.LENGTH_SHORT
                ).show()
                false
            }
        }

    }






}