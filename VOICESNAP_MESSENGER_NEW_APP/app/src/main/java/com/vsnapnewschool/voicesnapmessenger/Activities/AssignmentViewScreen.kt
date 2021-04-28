package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.vsnapnewschool.voicesnapmessenger.Models.DayCLass
import com.vsnapnewschool.voicesnapmessenger.Models.EventsImageClass
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.bottom_adds_items.*
import kotlinx.android.synthetic.main.parent_assignment_history.*
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import java.util.*

 class AssignmentViewScreen : BaseActivity(),View.OnClickListener {
    private val menulist = ArrayList<DayCLass>()
    var value: Boolean? = null
     var leaveClass: EventsImageClass? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        value = intent.extras!!.getBoolean("type")
        if(value as Boolean){
            theme.applyStyle(R.style.AppThemeParent,true)

        }else{
            theme.applyStyle(R.style.AppTheme,true)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_assignment_history)
        enableCrashLytics()
        scrollAdds(this,imageSlider)

        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)

        leaveClass = intent.getSerializableExtra("contentfile") as? EventsImageClass
        if(value as Boolean){
            parentActionbar()
            rytViewImage.setBackgroundResource(R.drawable.parent_blue_bg)
            LayoutDueTime.visibility=View.VISIBLE
            setTitle(getString(R.string.title_Assignment))
            enableSearch(false)
            ParentLayoutButtons.visibility=View.VISIBLE
            TeacherLayoutButtons.visibility=View.GONE

            rcyleRecipients!!.visibility=View.GONE
            rytDaymonth.visibility=View.GONE
            rytRecipients.visibility=View.GONE
            Parentbottomlayout.visibility=View.VISIBLE
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.clr_parent))

        }else{
            initializeActionBar()
            LayoutDueTime.setBackgroundResource(R.drawable.rectangle_orange)
            setTitle(getString(R.string.title_Assignment))
            enableSearch(false)
            TeacherLayoutButtons.visibility=View.VISIBLE
            ParentLayoutButtons.visibility=View.VISIBLE
            btnSubmit.visibility=View.GONE
            btnSubmissions.visibility=View.VISIBLE
            TeacherBottomLayout.visibility=View.VISIBLE
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.app_color))

        }
        ImageLength()
        rytViewImage?.setOnClickListener(this)

    }
     private fun ImageLength() {
        var menus = DayCLass("8th Standrard")
        menulist.add(menus)
        menus = DayCLass("4th Standrard ")
        menulist.add(menus)
        menus = DayCLass("9th Standrard ")
        menulist.add(menus)
        menus = DayCLass("1th Standrard ")
        menulist.add(menus)
        menus = DayCLass("6th Standrard ")
        menulist.add(menus)
     }
     override fun onClick(v: View?) {
         when(v?.id){
             R.id.rytViewImage->{
                 UtilConstants.viewFileActivity(this, leaveClass!!, value!!)
             }

             R.id.btnSubmit->{

             }
             R.id.btnSubmissions->{

             }
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

