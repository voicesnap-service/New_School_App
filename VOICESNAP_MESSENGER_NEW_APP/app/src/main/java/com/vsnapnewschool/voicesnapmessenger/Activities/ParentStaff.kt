package com.vsnapnewschool.voicesnapmessenger.Activities

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentStaffAdapter
import com.vsnapnewschool.voicesnapmessenger.Models.class_chat
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.recyclerview_layout.*
import java.util.*

class ParentStaff : BaseActivity(),View.OnClickListener {
    internal lateinit var StaffAdapter: ParentStaffAdapter
    private val menulist = ArrayList<class_chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.recyclerview_layout)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_staff))
        enableSearch(true)
        ContentMethod()

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        recyle_parent_bottom_layout.visibility= View.VISIBLE
        StaffAdapter = ParentStaffAdapter(menulist, this)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 2)
        recyclerview.setLayoutManager(mLayoutManager)
        recyclerview.addItemDecoration(ParentStaff.GridSpacingItemDecoration(2, true))
        recyclerview.setItemAnimator(DefaultItemAnimator())
        recyclerview.adapter = StaffAdapter
    }

    class GridSpacingItemDecoration(private val spanCount: Int, includeEdge: Boolean) :
        ItemDecoration() {
        private var spacing = 2
        private val includeEdge: Boolean
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view) // item position
            val column = position % spanCount // item column
            if (includeEdge) {
                outRect.left =
                    spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
                outRect.right =
                    (column - 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)
                if (position < spanCount) { // top edge
                    outRect.top = spacing
                }
                outRect.bottom = spacing // item bottom
            } else {
                outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
                outRect.right =
                    spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing // item top
                }
            }
        }

        init {
            spacing = spacing
            this.includeEdge = includeEdge
        }
    }
    private fun ContentMethod() {
        val Icons = intArrayOf(R.drawable.man, R.drawable.event1, R.drawable.album9)
        var menus = class_chat("Arvind Kumar", "Principal",  Icons[0],"5 mins")
        menulist.add(menus)
        menus = class_chat("Sanjita Akter", "Assistant Professor",  Icons[1],"15 min")
        menulist.add(menus)
        menus = class_chat("Sayed Eftioz", "Non Taching Staff", Icons[2], "1hour")
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