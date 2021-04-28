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
import com.vsca.schoolnewvsnap.Adapter.SelectedPreviewSectionsAdapter
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Adapters.SectionListAdapter
import com.vsnapnewschool.voicesnapmessenger.Adapters.SubjectAdapter
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkFinalSectionListener
import com.vsnapnewschool.voicesnapmessenger.Interfaces.checkSectionsListener
import com.vsnapnewschool.voicesnapmessenger.Models.SelecetedPreviewSection
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.*
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_ASSIGNMENT
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TEXT_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_TYPE
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.MENU_VOICE_HOMEWORK
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SchoolID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelectedSectionsForSubjects
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelectedStandardID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.StaffID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedFinalSectionList
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.selectedSectionsListforSubjecject
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.choose_specific_sections.*
import kotlinx.android.synthetic.main.pop_recipient.view.*
import retrofit2.Call
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChooseSpecificSections : BaseActivity(), checkSectionsListener, checkFinalSectionListener,
    View.OnClickListener {
    private var strlist: StringBuilder?=null
    private var StandardSectionList = ArrayList<StandardData>()
    private var SectionList = ArrayList<Section>()
    private var StandardNames = ArrayList<String>()
    var strlistname = ArrayList<String>()

    private var subjectList = ArrayList<SubjectsData>()
    internal lateinit var subjectAdapter: SubjectAdapter


    internal lateinit var sectionAdapter: SectionListAdapter
    var SelectedMutableSatandardSectionList = HashMap<String, MutableList<Section>>()
    var recipientpopup: PopupWindow? = null
    var dialog: View? = null

    var recipientadpt: SelectedPreviewSectionsAdapter? = null
    private var selectedPreviewSectionList = ArrayList<SelecetedPreviewSection>()
    private var finalSelectedSections = ArrayList<SelecetedPreviewSection>()

    var sections:String?=""

    private var Position: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_specific_sections)
        enableCrashLytics()
        initializeActionBar()
        setTitle("Choose sections")
        getSpecificStandardSections()
        btnPreview.setOnClickListener(this)
        btnNext.setOnClickListener(this)
        btnGetSubjects.setOnClickListener(this)

        if (MENU_TYPE == MENU_TEXT_HOMEWORK || MENU_TYPE == MENU_VOICE_HOMEWORK || MENU_TYPE == MENU_ASSIGNMENT) {
            btnGetSubjects.visibility = View.VISIBLE
        } else {
            btnGetSubjects.visibility = View.GONE
        }
    }

    private fun getSpecificStandardSections() {

        val ModuleType: String? = Util_shared_preferences.getModuleType(this)
        val MemberType: String? = Util_shared_preferences.getMemberType(this)
        val logintoken: String? = Util_shared_preferences.getLoginToken(this)

        val jsonObject = JsonObject()
        jsonObject.addProperty("login_token", logintoken)
        jsonObject.addProperty("module_type", ModuleType)
        jsonObject.addProperty("member_type", MemberType)
        jsonObject.addProperty("staff_id", StaffID)
        jsonObject.addProperty("school_id", SchoolID)
        Log.d("Request", jsonObject.toString())
        GifLoading.loading(this@ChooseSpecificSections, true)

        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
          apiInterface.getStandardSections(jsonObject)!!
            .enqueue(object : retrofit2.Callback<StandardSectionsResponse?> {
                override fun onResponse(
                    call: Call<StandardSectionsResponse?>?,
                    response: Response<StandardSectionsResponse?>?
                ) {
                    try {
                        GifLoading.loading(this@ChooseSpecificSections, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getStandardSectionsList", gson.toJson(response))
                        StandardSectionList.clear()
                        SelectedMutableSatandardSectionList.clear()

                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                StandardSectionList = responseBody.data as ArrayList<StandardData>

                                loadSpinnerStandards()

                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseSpecificSections,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseSpecificSections,
                                this@ChooseSpecificSections.getString(R.string.Service_unavailable)
                            )
                        }

                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<StandardSectionsResponse?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseSpecificSections, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseSpecificSections, t.toString())
                }
            })
    }


    private fun loadSpinnerStandards() {
        StandardNames.clear()
        StandardSectionList.forEach {
            StandardNames.add(it.standard_name)
        }
        if (spinnerStandards != null) {
            val adapter =
                ArrayAdapter(this@ChooseSpecificSections, R.layout.school_spin_title, StandardNames)
            adapter.setDropDownViewResource(R.layout.school_dropdown)
            spinnerStandards.adapter = adapter
            spinnerStandards.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    SelectedStandardID = StandardSectionList.get(position).standard_id
                    loadSections(position)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
    }

    private fun loadSections(position: Int) {
        selectedSectionsListforSubjecject.clear()
        Position = position
        Log.d("position", Position.toString())
        if (!SelectedMutableSatandardSectionList.containsKey(
                (StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                    Position
                ).standard_name)
            )
        ) {
            SelectedMutableSatandardSectionList.put(
                StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                    Position
                ).standard_name, mutableListOf()
            )
        }

        SectionList = StandardSectionList.get(position).section_list as ArrayList<Section>
        sectionAdapter = SectionListAdapter(
            SectionList,
            this@ChooseSpecificSections,
            SelectedMutableSatandardSectionList,
            StandardSectionList.get(
                position
            ).standard_id + "-" + StandardSectionList.get(Position).standard_name
        )
        val mLayoutManager = LinearLayoutManager(this@ChooseSpecificSections)
        recycleSections.layoutManager = mLayoutManager
        recycleSections.itemAnimator = DefaultItemAnimator()
        recycleSections.adapter = sectionAdapter
        sectionAdapter.notifyDataSetChanged()

    }

    override fun add(sections: Section?) {
        selectedSectionsListforSubjecject.add(sections!!)

        if (SelectedMutableSatandardSectionList.get(
                StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                    Position
                ).standard_name
            ) == null
        ) {
            SelectedMutableSatandardSectionList.get(
                StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                    Position
                ).standard_name
            )?.add(sections!!)
        } else {
            if (SelectedMutableSatandardSectionList.get(
                    StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                        Position
                    ).standard_name
                )!!.contains(sections)
            ) {
                SelectedMutableSatandardSectionList.get(
                    StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                        Position
                    ).standard_name
                )?.remove(sections!!)
            } else {
                SelectedMutableSatandardSectionList.get(
                    StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                        Position
                    ).standard_name
                )?.add(sections!!)

            }
        }
        setEnablePreviewandNext()

    }

    override fun remove(sections: Section?) {
        selectedSectionsListforSubjecject.remove(sections!!)

        SelectedMutableSatandardSectionList.get(
            StandardSectionList.get(Position).standard_id + "-" + StandardSectionList.get(
                Position
            ).standard_name
        )?.remove(sections!!)
        setEnablePreviewandNext()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnPreview -> {
                if (SelectedMutableSatandardSectionList.isNotEmpty()) {
                    selectedStandardSections()
                }

            }
            R.id.btnNext -> {
                getSelectedStandardsSections()
                selectedFinalSectionList = selectedPreviewSectionList
                if (selectedFinalSectionList != null) {
                    if(UtilConstants.selectedSubjectName!= null){
                        UtilConstants.previewScreens(this)
                    }else{
                        Toast.makeText(this,getString(R.string.str_selectSubject), Toast.LENGTH_LONG).show();
                    }

                }

            }
            R.id.btnGetSubjects -> {
                recycleSubjects.visibility = View.VISIBLE

                SelectedSectionsForSubjects=""
                sections=""
                selectedSectionsListforSubjecject.forEach {
                        var sectionID = it.section_id
                        sections = sections+sectionID+"~"
                }
                SelectedSectionsForSubjects=sections
                var selectedSection = SelectedSectionsForSubjects!!.substring(0,SelectedSectionsForSubjects!!.length-1)

                getAllSubjects(selectedSection)
            }
        }
    }

    fun getAllSubjects(selectedSection:String) {
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
        jsonObject.addProperty("section_id",selectedSection )
        Log.d("getSubjectRequest", jsonObject.toString())

        GifLoading.loading(this, true)
        var apiInterface: ApiInterface =
            APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getSubjects(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetAllSubjects?> {
                override fun onResponse(
                    call: Call<GetAllSubjects?>?,
                    response: Response<GetAllSubjects?>?
                ) {

                    try {
                        GifLoading.loading(this@ChooseSpecificSections, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getAllSubjectList", gson.toJson(response))

                        subjectList.clear()
                        if (response?.code() == 200) {
                            if (responseBody!!.status == 1) {


                                subjectList = responseBody.data as ArrayList<SubjectsData>
                                Log.d("SubjectListsize", subjectList.size.toString())

                                subjectAdapter = SubjectAdapter(subjectList, this@ChooseSpecificSections)
                                val mLayoutManager = LinearLayoutManager(
                                    this@ChooseSpecificSections,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                                recycleSubjects.layoutManager = mLayoutManager
                                recycleSubjects.itemAnimator =
                                    DefaultItemAnimator() as RecyclerView.ItemAnimator?
                                recycleSubjects.adapter = subjectAdapter

                            } else {
                                UtilConstants.customFailureAlert(
                                    this@ChooseSpecificSections,
                                    responseBody.message
                                )
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                this@ChooseSpecificSections,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                this@ChooseSpecificSections, this@ChooseSpecificSections?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }

                }

                override fun onFailure(call: Call<GetAllSubjects?>?, t: Throwable?) {
                    GifLoading.loading(this@ChooseSpecificSections, false)
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(this@ChooseSpecificSections, t.toString())
                }
            })
    }

    private fun selectedStandardSections() {

        getSelectedStandardsSections()

        val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        dialog = inflater.inflate(R.layout.pop_recipient, null)
        recipientpopup = PopupWindow(
            dialog,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
            true
        )
        recipientpopup?.showAtLocation(
            dialog,
            Gravity.CENTER,
            0,
            0
        )
        recipientpopup?.setContentView(dialog)
        val container = recipientpopup?.getContentView()?.getParent() as View
        val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val p = container.layoutParams as WindowManager.LayoutParams
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        p.dimAmount = 0.3f
        wm.updateViewLayout(container, p)

        dialog?.imgClose?.setOnClickListener(View.OnClickListener {
            recipientpopup?.dismiss()
        })
        dialog?.btnNext?.setOnClickListener {
            selectedFinalSectionList = finalSelectedSections

            if (selectedFinalSectionList != null) {
                UtilConstants.previewScreens(this)

                // openSchoolMenuScreens(this)
            }
        }
        val mLayoutManager: RecyclerView.LayoutManager =
            LinearLayoutManager(this@ChooseSpecificSections)
        dialog?.rycRecipient?.setLayoutManager(mLayoutManager)
        dialog?.rycRecipient?.setItemAnimator(DefaultItemAnimator())
        recipientadpt = SelectedPreviewSectionsAdapter(
            this@ChooseSpecificSections,
            this@ChooseSpecificSections,
            selectedPreviewSectionList
        )
        dialog?.rycRecipient?.setAdapter(recipientadpt)

    }

    private fun getSelectedStandardsSections() {
        finalSelectedSections.clear()
        selectedFinalSectionList.clear()
        selectedPreviewSectionList.clear()
        for ((key, value) in SelectedMutableSatandardSectionList) {
            val Name: String? = key
            val list = Name!!.split("-")
            val StandardSectionName = list.get(1)

            value.forEach {
                var sections = SelecetedPreviewSection(
                    StandardSectionName + "   -   " + it.section_name,
                    it.section_id
                )
                selectedPreviewSectionList.add(sections)
            }
        }
    }

    override fun addFinalSection(sections: SelecetedPreviewSection?) {
        finalSelectedSections.add(sections!!)
        setfinalbtnNextEnabled()

    }

    override fun removeFinalSection(sections: SelecetedPreviewSection?) {
        finalSelectedSections.remove(sections!!)
        setfinalbtnNextEnabled()

    }


    private fun setfinalbtnNextEnabled() {
        if (finalSelectedSections.size > 0) {
            dialog!!.btnNext.isEnabled = true
        } else {
            dialog!!.btnNext.isEnabled = false
        }
    }

    private fun setEnablePreviewandNext() {

        if(selectedSectionsListforSubjecject.size>0){
            btnGetSubjects.isEnabled = true

        }else{
            btnGetSubjects.isEnabled = false

        }
        if (SelectedMutableSatandardSectionList.size > 0) {
            btnNext.isEnabled = true
            btnPreview.isEnabled = true

        } else {
            btnNext.isEnabled = false
            btnPreview.isEnabled = false

        }


    }
}





