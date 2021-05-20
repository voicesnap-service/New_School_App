package com.vsnapnewschool.voicesnapmessenger.Activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.SelectedPreviewStudentTitleAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.StudentListAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.SubjectAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkFinalStudentListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkStudentListener
import com.vsnapnewschool.voicesnapmessenger.Models.SelectedPreviewStudent
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SchoolID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.StaffID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedFinalStudentList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.choose_specific_sections.*
import kotlinx.android.synthetic.main.choose_specific_sections.btnNext
import kotlinx.android.synthetic.main.choose_specific_sections.btnPreview
import kotlinx.android.synthetic.main.choose_specific_sections.spinnerStandards
import kotlinx.android.synthetic.main.choose_specific_students.*
import kotlinx.android.synthetic.main.pop_recipient.view.*
import retrofit2.Call
import retrofit2.Response

class ChooseSpecificStudents : BaseActivity(), checkStudentListener, checkFinalStudentListener,
    View.OnClickListener {
    private var StandardSectionList = ArrayList<StandardData>()
    private var StandardNames = ArrayList<String>()
    private var SectionNames = ArrayList<String>()
    private var StudentList = ArrayList<StudentData>()
    private var Position: Int = 0


    private var subjectList = ArrayList<SubjectsData>()
    internal lateinit var subjectAdapter: SubjectAdapter
    var SelectedStandardID: String? = null
    var SelectedSectionID: String? = null
    var SelectedStandardIDName: String? = null
    var SelectedSectionIDName: String? = null
    internal lateinit var studentAdapter: StudentListAdapter
    var SelectedMutableStudentList = HashMap<String, MutableList<StudentData>>()
    private var selectedPreviewStudentList = ArrayList<SelectedPreviewStudent>()
    private var finalSelectedStudents = ArrayList<SelectedPreviewStudent>()
    var recipientpopup: PopupWindow? = null
    var dialog: View? = null
    var recipientadpt: SelectedPreviewStudentTitleAdapter? = null

    private var selectStudent: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_specific_students)
        enableCrashLytics()

        initializeActionBar()
        setTitle("Choose students")
        btnPreview.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        btnGetSubject.setOnClickListener(this)
        getStandardSections()

        if (UtilConstants.MENU_TYPE == UtilConstants.MENU_ASSIGNMENT) {
            btnGetSubject.visibility = View.VISIBLE
            lblStudent.visibility = View.VISIBLE
            recycleStudents.visibility = View.VISIBLE
        } else {
            btnGetSubject.visibility = View.GONE
            lblStudent.visibility = View.VISIBLE
            recycleStudents.visibility = View.VISIBLE
        }


    }

    private fun getStandardSections() {
        val ModuleType: String? = Util_shared_preferences.getModuleType(this)
        val MemberType: String? = Util_shared_preferences.getMemberType(this)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", Logintoken)
        jsonObject.addProperty("module_type", ModuleType)
        jsonObject.addProperty("member_type", MemberType)
        jsonObject.addProperty("staff_id", StaffID)
        jsonObject.addProperty("school_id", SchoolID)
        Log.d("Request", jsonObject.toString())
        GifLoading.loading(this@ChooseSpecificStudents, true)

        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getStandardSections(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StandardSectionsResponse?> {
                override fun onResponse(
                    call: Call<StandardSectionsResponse?>?,
                    response: Response<StandardSectionsResponse?>?
                ) {
                    try {
                        GifLoading.loading(this@ChooseSpecificStudents, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getStandardSectionsList", gson.toJson(response))
                        StandardSectionList.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                StandardSectionList = responseBody.data as ArrayList<StandardData>
                                loadStandards()
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseSpecificStudents,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseSpecificStudents,
                                this@ChooseSpecificStudents.getString(R.string.Service_unavailable)
                            )
                        }

                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<StandardSectionsResponse?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseSpecificStudents, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseSpecificStudents, t.toString())
                }
            })
    }

    private fun loadStandards() {
        StandardNames.clear()
        StandardSectionList.forEach {
            StandardNames.add(it.standard_name)
        }
        if (spinnerStandards != null) {
            val adapter =
                ArrayAdapter(this@ChooseSpecificStudents, R.layout.school_spin_title, StandardNames)
            adapter.setDropDownViewResource(R.layout.school_dropdown)
            spinnerStandards.adapter = adapter
            spinnerStandards.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    SelectedStandardID = StandardSectionList.get(position).standard_id
                    SelectedStandardIDName = StandardSectionList.get(position).standard_id + "," + StandardSectionList.get(position).standard_name

                    loadSections(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
    }

    private fun loadSections(pos: Int) {
        SectionNames.clear()
        StandardSectionList.get(pos).section_list.forEach {
            SectionNames.add(it.section_name)
        }
        if (spinnerSections != null) {
            val adapter = ArrayAdapter(
                this@ChooseSpecificStudents,
                R.layout.school_spin_title,
                SectionNames
            )
            adapter.setDropDownViewResource(R.layout.school_dropdown)
            spinnerSections.adapter = adapter
            spinnerSections.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    Log.d("Selected Section ", SectionNames[position])
                    SelectedSectionID =
                        StandardSectionList.get(pos).section_list.get(position).section_id
                    SelectedSectionIDName =
                        StandardSectionList.get(pos).section_list.get(position).section_id + "," + StandardSectionList.get(
                            pos
                        ).section_list.get(position).section_name
                    getAllStudents()


                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
    }

    private fun getAllStudents() {

        val ModuleType: String? = Util_shared_preferences.getModuleType(this)
        val MemberType: String? = Util_shared_preferences.getMemberType(this)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", Logintoken)
        jsonObject.addProperty("module_type", ModuleType)
        jsonObject.addProperty("member_type", MemberType)
        jsonObject.addProperty("staff_id", StaffID)
        jsonObject.addProperty("school_id", SchoolID)
        jsonObject.addProperty("standard_id", SelectedStandardID)
        jsonObject.addProperty("section_id", SelectedSectionID)

        Log.d("SelectedStandardID", SelectedStandardID!!)
        Log.d("SelectedSectionID", SelectedSectionID!!)

        GifLoading.loading(this@ChooseSpecificStudents, true)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getAllStudents(jsonObject)!!
            .enqueue(object : retrofit2.Callback<AllStudentsResponse?> {
                override fun onResponse(
                    call: Call<AllStudentsResponse?>?,
                    response: Response<AllStudentsResponse?>?
                ) {

                    try {
                        GifLoading.loading(this@ChooseSpecificStudents, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getAllStudentList", gson.toJson(response))
                        StudentList.clear()
                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {
                                StudentList = responseBody.data as ArrayList<StudentData>
                                loadStudentsAdapter()

                            } else {
                                UtilConstants.customFailureAlert(
                                    this@ChooseSpecificStudents,
                                    responseBody.message
                                )
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseSpecificStudents,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseSpecificStudents,
                                this@ChooseSpecificStudents.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<AllStudentsResponse?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseSpecificStudents, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseSpecificStudents, t.toString())
                }
            })
    }


    private fun getAllSubjects(SelectedStandardID: String?, SelectedSectionID: String?) {

        val ModuleType: String? = Util_shared_preferences.getModuleType(this)
        val MemberType: String? = Util_shared_preferences.getMemberType(this)
        val Logintoken: String? = Util_shared_preferences.getLoginToken(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", Logintoken)
        jsonObject.addProperty("module_type", ModuleType)
        jsonObject.addProperty("member_type", MemberType)
        jsonObject.addProperty("staff_id", StaffID)
        jsonObject.addProperty("school_id", SchoolID)
        jsonObject.addProperty("standard_id", SelectedStandardID)
        jsonObject.addProperty("section_id", SelectedSectionID)
        Log.d("StudentsSubjectList", jsonObject.toString())

        GifLoading.loading(this@ChooseSpecificStudents, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getSubjects(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetAllSubjects?> {
                override fun onResponse(
                    call: Call<GetAllSubjects?>?,
                    response: Response<GetAllSubjects?>?
                ) {

                    try {
                        GifLoading.loading(this@ChooseSpecificStudents, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getAllSubjectList", gson.toJson(response))
                        StudentList.clear()
                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {

                                subjectList = responseBody.data as ArrayList<SubjectsData>
                                Log.d("SubjectListsize", subjectList.size.toString())

                                subjectAdapter =
                                    SubjectAdapter(subjectList, this@ChooseSpecificStudents)
                                val mLayoutManager = LinearLayoutManager(
                                    this@ChooseSpecificStudents,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                recycleSubject.layoutManager = mLayoutManager
                                recycleSubject.itemAnimator =
                                    DefaultItemAnimator() as RecyclerView.ItemAnimator?
                                recycleSubject.adapter = subjectAdapter
                            } else {
                                UtilConstants.customFailureAlert(
                                    this@ChooseSpecificStudents,
                                    responseBody.message
                                )
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseSpecificStudents,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseSpecificStudents,
                                this@ChooseSpecificStudents.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<GetAllSubjects?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseSpecificStudents, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseSpecificStudents, t.toString())
                }
            })
    }

    private fun loadStudentsAdapter() {

        if (!SelectedMutableStudentList.containsKey((SelectedStandardIDName + "-" + SelectedSectionIDName))) {
            SelectedMutableStudentList.put(
                SelectedStandardIDName + "-" + SelectedSectionIDName, mutableListOf()
            )
        }
        studentAdapter = StudentListAdapter(
            StudentList,
            this@ChooseSpecificStudents,
            SelectedMutableStudentList,
            SelectedStandardIDName + "-" + SelectedSectionIDName
        )
        val mLayoutManager = LinearLayoutManager(this@ChooseSpecificStudents)
        recycleStudents.layoutManager = mLayoutManager
        recycleStudents.itemAnimator = DefaultItemAnimator()
        recycleStudents.adapter = studentAdapter
        studentAdapter.notifyDataSetChanged()

    }

    override fun add(students: StudentData?) {
        if (SelectedMutableStudentList.get(SelectedStandardIDName + "-" + SelectedSectionIDName) == null) {
            SelectedMutableStudentList.get(SelectedStandardIDName + "-" + SelectedSectionIDName)
                ?.add(students!!)
        } else {
            if (SelectedMutableStudentList.get(SelectedStandardIDName + "-" + SelectedSectionIDName)!!
                    .contains(students)
            ) {
                SelectedMutableStudentList.get(SelectedStandardIDName + "-" + SelectedSectionIDName)
                    ?.remove(students!!)
            } else {
                SelectedMutableStudentList.get(SelectedStandardIDName + "-" + SelectedSectionIDName)
                    ?.add(students!!)

            }
        }
        setEnablePreviewandNext()
    }

    override fun remove(students: StudentData?) {
        SelectedMutableStudentList.get(SelectedStandardIDName + "-" + SelectedSectionIDName)
            ?.remove(students!!)
        setEnablePreviewandNext()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnPreview -> {

                selectStudent = 0
                finalSelectedStudents.clear()

                if (SelectedMutableStudentList != null) {
                    selectedStudents()
                } else {
                    UtilConstants.normalToast(this, "Please select the atleast one student")
                }

            }
            R.id.btnNext -> {
                getSelectedStandardsSectionsStudents()
                selectedFinalStudentList = selectedPreviewStudentList

                if (UtilConstants.MENU_TYPE == UtilConstants.MENU_TEXT_HOMEWORK || UtilConstants.MENU_TYPE == UtilConstants.MENU_VOICE_HOMEWORK || UtilConstants.MENU_TYPE == UtilConstants.MENU_ASSIGNMENT) {

                    if (UtilConstants.selectedFinalSectionList != null) {
                        if (UtilConstants.selectedSubjectName != null) {
                            UtilConstants.previewScreens(this)
                        } else {
                            Toast.makeText(
                                this,
                                getString(R.string.str_selectSubject),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }
                }else{

                    if (selectedFinalStudentList != null) {
                        UtilConstants.previewScreens(this)

                    } else {
                        UtilConstants.normalToast(this, "Please select the atleast one student")
                    }
                }


            }
            R.id.btnGetSubject -> {
                recycleSubject.visibility = View.VISIBLE

                getAllSubjects(SelectedStandardID,SelectedSectionID)
            }

        }
    }

    private fun selectedStudents() {
        getSelectedStandardsSectionsStudents()
        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialog = inflater.inflate(R.layout.pop_recipient, null)
        recipientpopup = PopupWindow(
            dialog,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        recipientpopup?.showAtLocation(dialog, Gravity.CENTER, 0, 0)
        recipientpopup?.setContentView(dialog)
        val container = recipientpopup?.getContentView()?.getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.3f
        wm.updateViewLayout(container, p)

        dialog?.lblrecipient?.setText("Students")

        dialog?.imgClose?.setOnClickListener(View.OnClickListener {
            recipientpopup!!.dismiss()
        })
        dialog?.btnNext?.setOnClickListener {
            selectedFinalStudentList = finalSelectedStudents
            Log.d("selectedstudentlist", selectedFinalStudentList.size.toString())

            if (selectedFinalStudentList != null) {
                UtilConstants.previewScreens(this)

            }
        }
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this@ChooseSpecificStudents)
        dialog?.rycRecipient?.setLayoutManager(mLayoutManager)
        dialog?.rycRecipient?.setItemAnimator(DefaultItemAnimator())
        recipientadpt = SelectedPreviewStudentTitleAdapter(
            this@ChooseSpecificStudents,
            this@ChooseSpecificStudents,
            selectedPreviewStudentList
        )
        dialog?.rycRecipient?.setAdapter(recipientadpt)

    }

    private fun getSelectedStandardsSectionsStudents() {
        finalSelectedStudents.clear()
        selectedFinalStudentList.clear()
        selectedPreviewStudentList.clear()
        for ((key, value) in SelectedMutableStudentList) {
            val Name: String? = key
            val list = Name?.split("-")
            val StandardNameID = list?.get(0)
            val SectionNameID = list?.get(1)
            val Standard = StandardNameID?.split(",")
            val standardName = Standard?.get(1)
            val Section = SectionNameID?.split(",")
            val sectionID = Section?.get(0)
            val sectionName = Section?.get(1)

            var selectedStudents = ArrayList<StudentData>()
            selectedStudents.clear()

            value.forEach {
                selectedStudents.add(it)
            }
            var sections = SelectedPreviewStudent(
                standardName + "   -   " + sectionName,
                sectionID,
                selectedStudents
            )
            selectedPreviewStudentList.add(sections)
        }
    }

    private fun setEnablePreviewandNext() {
        if (SelectedMutableStudentList.size > 0) {
            btnNext.isEnabled = true
            btnPreview.isEnabled = true
        } else {
            btnNext.isEnabled = false
            btnPreview.isEnabled = false

        }
    }

    private fun setfinalbtnNextEnabled() {
        if (finalSelectedStudents.size > 0) {
            dialog!!.btnNext.isEnabled = true
        } else {
            dialog!!.btnNext.isEnabled = false
        }
    }

    override fun addFinalStudent(
        students: StudentData?,
        standardSectionName: String?,
        sectionID: String?,
        TitlePosition: Int?
    ) {
        var selectedStudents = ArrayList<StudentData>()
        selectedStudents.add(students!!)
        var sections = SelectedPreviewStudent(standardSectionName, sectionID, selectedStudents)

        if (selectStudent == 1) {
            finalSelectedStudents.set(TitlePosition!!, sections)
        } else {
            finalSelectedStudents.add(TitlePosition!!, sections)
        }
        setfinalbtnNextEnabled()
    }

    override fun removeFinalStudent(
        students: StudentData?,
        standardSectionName: String?,
        sectionID: String?,
        TitlePosition: Int?
    ) {
        var selectedStudents = ArrayList<StudentData>()
        selectedStudents.remove(students!!)
        var sections = SelectedPreviewStudent(standardSectionName, sectionID, selectedStudents)
        finalSelectedStudents.set(TitlePosition!!, sections)
        selectStudent = 1
        setfinalbtnNextEnabled()
    }

}