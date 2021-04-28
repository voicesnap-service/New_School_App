package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.TextHistoryAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.textHistoryListener
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class TextHistory : Fragment() {
    private val menulist = ArrayList<Text_Class>()
    internal lateinit var textHistoryAdapter: TextHistoryAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.recyclerview_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adds_layout.visibility=View.GONE
        ImageLength()
        textHistoryAdapter = TextHistoryAdapter(menulist,activity, object : textHistoryListener {
            override fun textHistoryClick (holder: TextHistoryAdapter.MyViewHolder, text_info: Text_Class) {
                holder.lnrText.setOnClickListener({
                    UtilConstants.teacherTextHistoryActivity(activity!!)

                })

            }
        })
        val mLayoutManager = LinearLayoutManager(activity)
        recyclerview.layoutManager = mLayoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = textHistoryAdapter

    }
    private fun ImageLength() {
            var menus = Text_Class(
                "Kotlin has great support and many contributions from the community, which is growing all over the world",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class(
                "Kotlin has great support and many contributions from the community, which is growing all over the world",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class(
                "Kotlin has great support and many contributions from the community, which is growing all over the world",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class(
                "Kotlin has great support and many contributions from the community, which is growing all over the world",
                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

            menus = Text_Class( "Kotlin has great support and many contributions from the community, which is growing all over the world",

                " 4233 Recipients ",
                " Sent Successfully ",
                "Sent on Jun 05 at 10.30 am"
            )
            menulist.add(menus)

        }
    }



