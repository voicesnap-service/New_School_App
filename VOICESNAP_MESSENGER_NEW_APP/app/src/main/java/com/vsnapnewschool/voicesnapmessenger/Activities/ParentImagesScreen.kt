package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentImageAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.imageListener
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelcetedFileList
import kotlinx.android.synthetic.main.activity_image_grid.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*


class ParentImagesScreen : BaseActivity(),View.OnClickListener {
    var imageadapter:ParentImageAdapter? = null
    private val menulist = ArrayList<EventsImageClass>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_grid)
        enableCrashLytics()
        scrollAdds(this,imageSlider)
        parentActionbar()
        setTitle(getString(R.string.title_Images))
        enableSearch(true)
        btnNext.visibility=View.GONE
        parent_bottom_layout.visibility=View.VISIBLE
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        recyleImages.layoutManager = GridLayoutManager(this,3)

        imageadapter = ParentImageAdapter(SelcetedFileList, this, object : imageListener {
            override fun onimageClick (holder: ParentImageAdapter.MyViewHolder, text_info: String) {

                holder.imgGrid.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View) {
                        UtilConstants.ImageViewScreen(this@ParentImagesScreen,true,text_info)

                    }
                })
            }
        })
        recyleImages.adapter = imageadapter
        ImageLength()

    }
    private fun ImageLength() {
        var movieModel = EventsImageClass(R.drawable.event1,"05","Aug","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)

        movieModel = EventsImageClass(R.drawable.event2,"30","Jun","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.event3,"09","Sep","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.event4,"06","Aug","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.album6,"06","Aug","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.album9,"06","Aug","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.man,"06","Aug","AnnualDay 2020","Main Audiotorium","image")
        menulist.add(movieModel)

        movieModel = EventsImageClass( R.drawable.event4,"06","Aug","AnnualDay 2020","Main Audiotorium","")
        menulist.add(movieModel)

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
