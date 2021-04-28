package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoMonthAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewallAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.VideoviewconAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.videoViewListener
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.Models.Text_Class
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_parent_video.*
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import java.util.*

class ParentVideo : BaseActivity(),View.OnClickListener{
    internal lateinit var videoViewConAdapter: VideoviewconAdapter
    internal lateinit var videoMonthAdapter: VideoMonthAdapter
    internal lateinit var videoViewAllAdapter: VideoviewallAdapter
    private val menulist = ArrayList<Text_Class>()
    private val videolist = ArrayList<Text_Class>()
    private val menulist1 = ArrayList<DayCLass>()
    private  val row_index: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent_video)
        enableCrashLytics()
        parentActionbar()
        setTitle(getString(R.string.title_Video))
        enableSearch(true)
        scrollAdds(this,imageSlider)
        ImageLength()
        Day()

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        videoViewConAdapter = VideoviewconAdapter(menulist,this, object : videoViewListener {
            override fun videoViewClick (holder: VideoviewconAdapter.MyViewHolder, text_info: Text_Class) {
                holder.imgthumb.setOnClickListener(object : View.OnClickListener{
                    override fun onClick(v: View) {
                        UtilConstants.parentVideoViewActivity(this@ParentVideo)

                    }
                })

            }
        })

        rcyViewVideo.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true))
        rcyViewVideo.setItemAnimator(DefaultItemAnimator())
        rcyViewVideo.setAdapter(videoViewConAdapter)
        videoViewConAdapter.notifyDataSetChanged()
        videoMonthAdapter = VideoMonthAdapter(menulist1,this,row_index, object :
            VideoMonthAdapter.BtnClickListener {
            override fun onBtnClick(position: Int) {

                if(position==0){
                    VideoAll()
                    videoViewAllAdapter = VideoviewallAdapter(videolist,this@ParentVideo)
                }else if(position==1){
                    videoViewAllAdapter = VideoviewallAdapter(menulist,this@ParentVideo )
                    Day()
                }else{
                    VideoAll()
                    videoViewAllAdapter = VideoviewallAdapter(videolist,this@ParentVideo)
                }
                val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@ParentVideo)
                rcyVideo.setLayoutManager(mLayoutManager)
                rcyVideo.setItemAnimator(DefaultItemAnimator())
                rcyVideo.setAdapter(videoViewAllAdapter)
                videoViewAllAdapter.notifyDataSetChanged()
            }
        })

        rcyDay.setItemAnimator(DefaultItemAnimator())
        rcyDay.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
        rcyDay.setAdapter(videoMonthAdapter)
        videoMonthAdapter.notifyDataSetChanged()


    }

    private fun ImageLength() {

        menulist.clear()

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
    private fun Day() {
        menulist1.clear()

        var menus = DayCLass("Mon")
        menulist1.add(menus)

        menus = DayCLass("Tue")
        menulist1.add(menus)

        menus = DayCLass("Wed")
        menulist1.add(menus)

        menus = DayCLass("Thu")
        menulist1.add(menus)

        menus = DayCLass("Fri")
        menulist1.add(menus)

        menus = DayCLass("Sat")
        menulist1.add(menus)

    }

    private fun VideoAll() {
        videolist.clear()
        var menus = Text_Class(
            "Kotlin has great support and many contributions from the community, which is growing all over the world",
            " 4233 Recipients ",
            " Sent Successfully ",
            "Sent on Jun 05 at 10.30 am")
        videolist.add(menus)

        menus = Text_Class(
            "Kotlin has great support and many contributions from the community, which is growing all over the world",
            " 4233 Recipients ",
            " Sent Successfully ",
            "Sent on Jun 05 at 10.30 am")
        videolist.add(menus)

        menus = Text_Class(
            "Kotlin has great support and many contributions from the community, which is growing all over the world",
            " 4233 Recipients ",
            " Sent Successfully ",
            "Sent on Jun 05 at 10.30 am")
        videolist.add(menus)
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
