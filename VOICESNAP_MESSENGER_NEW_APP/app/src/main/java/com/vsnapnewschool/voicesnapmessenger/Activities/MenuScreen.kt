package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_menu.*


class MenuScreen : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                var fbmToken = it.result.toString()
                Log.d("deviceToken", fbmToken)
            }
        }


        events.setOnClickListener {
            UtilConstants.eventsActivity(this)

        }
        assignment.setOnClickListener {
            UtilConstants.assignMentActivity(this)
        }
        Homework.setOnClickListener {
            UtilConstants.homeWorkActivity(this)

        }
        images.setOnClickListener {
            UtilConstants.imagesActivity(this)

        }
        chat.setOnClickListener {
            UtilConstants.chatActivity(this)
        }

        notice.setOnClickListener {
            UtilConstants.noticeBoardActivity(this)
        }
        approveleave.setOnClickListener {
            UtilConstants.approveLeaveActivity(this)
        }
        text.setOnClickListener {
            UtilConstants.textMesageActivity(this)
        }
        circular.setOnClickListener {
            UtilConstants.circularActivity(this)
        }
        timetable.setOnClickListener {
            UtilConstants.timeTableActivity(this)
        }
        attendance.setOnClickListener {
            UtilConstants.attendanceActivity(this)
        }
        voice.setOnClickListener {
            UtilConstants.voiceMessageActivity(this)
        }
        video.setOnClickListener {
            UtilConstants.videoActivity(this)
        }

    }


}
