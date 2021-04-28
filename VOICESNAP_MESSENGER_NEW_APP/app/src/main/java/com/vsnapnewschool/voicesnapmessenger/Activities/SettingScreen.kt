package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.PopupWindow
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.parent_activity_settings_screen.*
import kotlinx.android.synthetic.main.parent_activity_settings_screen.Logout
import kotlinx.android.synthetic.main.parent_bottom_menus.*
import kotlinx.android.synthetic.main.parent_settings_scroll.*
import kotlinx.android.synthetic.main.popup_changepassword.view.*

class SettingScreen : BaseActivity(), View.OnClickListener {
    var recipientpopup: PopupWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val MemberType: String? = Util_shared_preferences.getMemberType(this)
        if ((MemberType.equals("3")) || (MemberType.equals("2")) || (MemberType.equals("6")) || (MemberType.equals(
                "1"
            ))
        ) {
            theme.applyStyle(R.style.AppTheme, true)
        } else {
            theme.applyStyle(R.style.AppThemeParent, true)
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parent_settings_scroll)
        enableCrashLytics()

        if ((MemberType.equals("3")) || (MemberType.equals("2")) || (MemberType.equals("6")) || (MemberType.equals(
                "1"
            ))
        ) {
            initializeActionBar()
            imgProfile.setBackgroundResource(R.drawable.circle_orange_bg)
            TeacherBottomLayout.visibility = View.VISIBLE
        } else {
            parentActionbar()
            parent_bottom_layout.visibility = View.VISIBLE

        }
        setTitle(getString(R.string.title_settings))
        enableSearch(false)
        imgchat?.setOnClickListener(this)
        imgHomeMenu?.setOnClickListener(this)
        imgSettings?.setOnClickListener(this)
        imgSettings?.setImageResource(R.drawable.settings_white)
        imgSettings?.setBackgroundResource(R.drawable.bg_gradient_parent)
        imgSettings.setPadding(8, 8, 8, 8)

        ProfileLayout?.setOnClickListener(this)
        ChangePassword?.setOnClickListener(this)
        Changelanguage?.setOnClickListener(this)
        Logout?.setOnClickListener(this)
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ProfileLayout -> {
                profilePopUp()
            }
            R.id.ChangePassword -> {
                ChangePasswordPopup()
            }
            R.id.Changelanguage -> {
//                ChangeLanguage()
            }

            R.id.imgchat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgHomeMenu -> {
                UtilConstants.imgHomeIntent(this)
            }
            R.id.Logout -> {
                Util_shared_preferences.putUserLoggedIn(this, false)
                SchoolAPIServices.logoutFromSameDevice(this)
            }
            R.id.imgTeacherChat -> {

            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
        }
    }
    private fun profilePopUp() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialog: View = inflater.inflate(R.layout.activity_profile, null)
        recipientpopup = PopupWindow(
            dialog,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        recipientpopup!!.showAtLocation(dialog, Gravity.CENTER, 0, 0)
        recipientpopup!!.setContentView(dialog)
        val container = recipientpopup!!.getContentView().getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.8f
        wm.updateViewLayout(container, p)
        dialog.imgLeftArrow.setOnClickListener(View.OnClickListener {
            recipientpopup!!.dismiss()
        })
    }


    private fun ChangePasswordPopup() {
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialog: View = inflater.inflate(R.layout.popup_changepassword, null)
        recipientpopup = PopupWindow(
            dialog,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        recipientpopup!!.showAtLocation(dialog, Gravity.CENTER, 0, 0)
        recipientpopup!!.setContentView(dialog)
        val container = recipientpopup!!.getContentView().getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND

        p.dimAmount = 0.8f
        wm.updateViewLayout(container, p)
        dialog.imgCngePassword.setOnClickListener(View.OnClickListener {
            recipientpopup!!.dismiss()
        })
    }


}
