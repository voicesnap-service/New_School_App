package com.vsnapnewschool.voicesnapmessenger.Activities

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vsnapnewschool.voicesnapmessenger.Adapters.ChatConversationAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.ParentChatConversationAdapter
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetStaffAnsChatCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetStaffChatScreenCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetStudentChatScreenCallBack
import com.vsnapnewschool.voicesnapmessenger.CallBacks.GetSutudentAskQueCallBack
import com.vsnapnewschool.voicesnapmessenger.Interfaces.PaginationScrollListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.PopupListener
import com.vsnapnewschool.voicesnapmessenger.Network.SchoolAPIServices
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentChatData
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.StudentChatScreenResponse
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import kotlinx.android.synthetic.main.activity_bottom_menus.*
import kotlinx.android.synthetic.main.activity_chat_screen_messsage.*
import java.util.*

class ChatConversation : BaseActivity(), View.OnClickListener,GetStaffChatScreenCallBack,
    GetStudentChatScreenCallBack,GetSutudentAskQueCallBack, GetStaffAnsChatCallBack {
    internal lateinit var chatConversationAdapter: ChatConversationAdapter
    internal lateinit var parentchatConversationAdapter: ParentChatConversationAdapter
    var StaffChatscreenDetails = ArrayList<ChatData>()
    var StudentChatscreenDetails = ArrayList<StudentChatData>()
    lateinit var teacherChatAnswer: ChatData
    var type : Boolean = false
    var standardid:String = ""
    var sectionid:String = ""
    var subjectid:String = ""
    var staffid:String = ""
    var staffname:String = ""
    var questionid:String = ""
    var isclassteacher:Int = 0
    var isChangeAnswer = "0"

    var chatCount = 0
    var offset :Int= 0
    var limit = 0

    var Loading = false
    var LastPage = false
    var totalPages = 0
        get() {
            return field
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        type = intent.extras!!.getBoolean("type")

        if(type){
            setTheme(R.style.AppTheme)

        }else{
            setTheme(R.style.AppThemeParent)

        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen_messsage)
        enableCrashLytics()
        imgTeacherChat?.setOnClickListener(this)
        imgTeacherHomeMenu?.setOnClickListener(this)
        imgTeacherSettings?.setOnClickListener(this)
        btnReplyall?.setOnClickListener(this)
        btnReplyprivate?.setOnClickListener(this)
        imgSend?.setOnClickListener(this)
        lblContent.visibility=View.GONE
        imgSend.visibility=View.GONE
        rlbtns.visibility=View.GONE

        if(type){
            initializeActionBar()
            setTitle(getString(R.string.title_Chat))
            enableSearch(true)
            imgSend.setImageResource(R.drawable.teacherchat_send)
            UtilConstants.chatstandardid = intent.getStringExtra("standardid")!!
            UtilConstants.chatsectionid = intent.getStringExtra("sectionid")!!
            UtilConstants.chatsubjectid = intent.getStringExtra("subjectid")!!
            UtilConstants.chatisclassteacher = intent.getIntExtra("isclassteacher",0)

            SchoolAPIServices.StaffChatScreen(this,offset,this)


        }else{
            parentActionbar()
            setTitle(getString(R.string.title_Chat))
            enableSearch(true)
            imgSend.setImageResource(R.drawable.parentchat_send)
            rlbtns.visibility=View.GONE
            lblContent.visibility=View.VISIBLE
            imgSend.visibility=View.VISIBLE
            UtilConstants.chatstaffid = intent.getStringExtra("staffid")!!
            UtilConstants.chatsubjectid = intent.getStringExtra("subjectid")!!
            UtilConstants.chatstaffname = intent.getStringExtra("staffname")!!
            UtilConstants.chatisclassteacher = intent.getIntExtra("isclassteacher",0)
            StudentAPIServices.StudentChatScreen(this,offset,this)
        }

        recyclerChat.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) { recyclerChat.addOnScrollListener(object :
                PaginationScrollListener(recyclerChat.getLayoutManager() as LinearLayoutManager) {
                override fun isLastPage(): Boolean {
                    return LastPage
                }

                override fun loadMoreItems() {
                    Loading = true
                    if(type) {
                        if (Loading && StaffChatscreenDetails.size < chatCount) {
                            SchoolAPIServices.StaffChatScreen(this@ChatConversation, offset,this@ChatConversation)
                        } else {
                            Loading = false
                        }
                    }
                    else{
                        if (Loading && StudentChatscreenDetails.size < chatCount) {
                            StudentAPIServices.StudentChatScreen(this@ChatConversation, offset,this@ChatConversation)
                        } else {
                            Loading = false
                        }
                    }
                }

                override fun getTotalPageCount(): Int {
                    return totalPages
                }

                override fun isLoading(): Boolean {
                    return Loading
                }


            })
            }
        })

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.imgTeacherChat -> {
                //  setChatClick(imgChat?, imgHome?, imgProfile?)
            }
            R.id.imgTeacherHomeMenu -> {
                UtilConstants.imgTeacherHomeIntent(this)
            }
            R.id.imgTeacherSettings -> {
                UtilConstants.imgProfileIntent(this)
            }
            R.id.btnReplyall -> {
                if(!lblContent.text.isEmpty()){
                    SchoolAPIServices.StaffAnswerChat(this,lblContent.text.toString(),"2",questionid,isChangeAnswer,this)
                }
            }
            R.id.btnReplyprivate -> {
                if(!lblContent.text.isEmpty()){
                    SchoolAPIServices.StaffAnswerChat(this,lblContent.text.toString(),"1",questionid,isChangeAnswer,this)

                }
            }
            R.id.imgSend -> {
                if(!lblContent.text.isEmpty()){
                    StudentAPIServices.StudentAskQuestion(this,lblContent.text.toString(),this)
                }
            }

        }
    }



    fun Adapterview(){
        chatConversationAdapter = ChatConversationAdapter(StaffChatscreenDetails,this,type,object:PopupListener{
            override fun click(selected: String?, teacherChat: ChatData?) {
                rlbtns.visibility=View.VISIBLE
                lblContent.visibility=View.VISIBLE
                imgSend.visibility=View.GONE
                teacherChatAnswer= teacherChat!!
                questionid= teacherChat!!.question_id
                if (selected == "Answer") {
                    isChangeAnswer = "0"
                } else {
                    isChangeAnswer = "1"
                }
            }

        })
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerChat.layoutManager = mLayoutManager
        recyclerChat.itemAnimator = DefaultItemAnimator()
        recyclerChat.adapter = chatConversationAdapter
        chatConversationAdapter.notifyDataSetChanged()
    }


    fun ParentAdapterview() {
        parentchatConversationAdapter =
            ParentChatConversationAdapter(StudentChatscreenDetails, this, staffname)


        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerChat.layoutManager = mLayoutManager
        recyclerChat.itemAnimator = DefaultItemAnimator()
        recyclerChat.adapter = parentchatConversationAdapter
        parentchatConversationAdapter.notifyDataSetChanged()
    }

    override fun callbackstafchatscreen(responseBody: StaffChatScreenResponse,currentoffset: Int) {

        if (currentoffset == 0) {
            limit = responseBody.data.limit.toInt()
            if (limit > 0) {
                chatCount = responseBody.data.chat_count
                offset = responseBody.data.off_set.toInt() + 1
                StaffChatscreenDetails.clear()
                StaffChatscreenDetails =
                    responseBody.data.chat_data as ArrayList<ChatData>
                Adapterview()

            }
        } else {
            StaffChatscreenDetails.addAll( responseBody.data.chat_data as ArrayList<ChatData>)
            Adapterview()
            LastPage = currentoffset == totalPages - 1
            offset = responseBody.data.off_set.toInt() + 1
            if (LastPage) Loading = true else Loading = false

        }

    }

    override fun callbackstudentchatscreen(responseBody: StudentChatScreenResponse,currentoffset: Int) {


        if (currentoffset == 0) {
            limit = responseBody.data.limit.toInt()
            if (limit > 0) {
                chatCount = responseBody.data.chat_count
                offset = responseBody.data.off_set.toInt() + 1
                StudentChatscreenDetails.clear()
                StudentChatscreenDetails =
                    responseBody.data.chat_data as ArrayList<StudentChatData>
                ParentAdapterview()

            }
        } else {
            StudentChatscreenDetails.addAll(responseBody.data.chat_data as ArrayList<StudentChatData>)
            ParentAdapterview()
            LastPage = currentoffset == totalPages - 1
            offset = responseBody.data.off_set.toInt() + 1
            if (LastPage) Loading = true else Loading = false

        }


    }

    override fun callbackstudentaskque(response: StudentChatScreenResponse) {
        lblContent.setText("")
        StudentChatscreenDetails.addAll(response.data.chat_data as ArrayList<StudentChatData>)
        ParentAdapterview()
    }

    override fun callbackstaffanschat(response: StaffChatAnswerResponse) {
        lblContent.setText("")
        val index: Int = StaffChatscreenDetails.indexOf(teacherChatAnswer)
        StaffChatscreenDetails.get(index).answered_on =response.data.chat_data.get(0).answered_on
        StaffChatscreenDetails.get(index).change_answer = response.data.chat_data.get(0).change_answer.toString()
        StaffChatscreenDetails.get(index).reply_type =response.data.chat_data.get(0).reply_type
        StaffChatscreenDetails.get(index).question_id =response.data.chat_data.get(0).question_id
        StaffChatscreenDetails.get(index).answer =response.data.chat_data.get(0).answer

        Adapterview()
    }

}
