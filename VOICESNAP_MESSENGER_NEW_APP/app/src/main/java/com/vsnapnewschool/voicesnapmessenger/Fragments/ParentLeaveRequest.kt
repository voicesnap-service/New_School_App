package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.vsca.vsnapvoicecollege.Rest.APIClient
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.Network.ApiInterface
import com.vsnapnewschool.voicesnapmessenger.Network.ApiRequestValues
import com.vsnapnewschool.voicesnapmessenger.Network.StudentAPIServices
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.GetLeaveType
import com.vsnapnewschool.voicesnapmessenger.ParentServiceModelResponse.LeaveTypeData
import com.vsnapnewschool.voicesnapmessenger.R
import com.vsnapnewschool.voicesnapmessenger.ServiceResponseModels.StatusMessageResponse
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.SelectedLeaveID
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.UtilConstants.Companion.customToast
import com.vsnapnewschool.voicesnapmessenger.UtilCommon.Util_shared_preferences
import com.vsnapnewschool.voicesnapmessenger.Util_Common.GifLoading
import kotlinx.android.synthetic.main.leave_request_apply.*
import kotlinx.android.synthetic.main.leave_request_apply.edDescription
import retrofit2.Call
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ParentLeaveRequest : Fragment(), View.OnClickListener {
    var cal = Calendar.getInstance()
    var FromDate: String? = null
    var Todate: String? = null
    val myCalendar = Calendar.getInstance()
    private var leavetypeList = ArrayList<LeaveTypeData>()
    private var leaveNames = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater?.inflate(
            R.layout.leave_request_scroll,
            container, false
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lblstartdate.setHint("From")
        lblenddate.setHint("To")
        lblstartdate?.setOnClickListener(this)
        lblenddate?.setOnClickListener(this)
        btndiscard?.setOnClickListener(this)
        btnApply?.setOnClickListener(this)

        getLeaveType()
    }

    override fun onClick(v: View?) {

        when (v!!.id) {
            R.id.lblstartdate -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)

                FromDate()
            }
            R.id.lblenddate -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
                ToDAte()

            }
            R.id.btndiscard -> {
                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)

                activity?.onBackPressed()

            }
            R.id.btnApply -> {


                (activity as BaseActivity?)!!.HideKeyboard_Fragment(context as Activity?)
                if (lblstartdate.text.toString().equals("From")) {
                    UtilConstants.normalToast(activity, "Choose From Date")

                } else if (lblenddate.text.toString().equals("To")) {
                    UtilConstants.normalToast(activity, "Choose To Date")

                } else if (edDescription.text.toString().equals("")) {
                    UtilConstants.normalToast(activity, "Please Enter Reason")

                } else {
                    UtilConstants.LeaveOtherDescription = edOtherDescription.text.toString()
                    UtilConstants.LeaveReason = edDescription.text.toString()
                    Log.d("testApi", "testAPi")
                    StudentAPIServices.studentApplyLeave(context as Activity?)


                }

            }
        }
    }

    private fun ToDAte() {
        val date =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                updateLabel1(lblenddate)
            }

        val datePickerDialog = DatePickerDialog(
            activity!!,
            R.style.ParentCalendarTheme,
            date,
            myCalendar[Calendar.YEAR],
            myCalendar[Calendar.MONTH],
            myCalendar[Calendar.DAY_OF_MONTH]
        )
        if (lblstartdate.getText().toString().isEmpty()) {
            alert("Please Choose From Date")
        } else {
            val myFormat = "dd/MM/yyyy"
            val sdf =
                SimpleDateFormat(myFormat, Locale.FRANCE)
            var datefrom: Date? = null
            try {
                datefrom = sdf.parse(FromDate!!)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            datePickerDialog.datePicker.minDate = datefrom!!.time
            //                datePickerDialog.getDatePicker().setMaxDate(dateto.getTime());
            datePickerDialog.show()

        }
    }

    private fun FromDate() {
        if (lblstartdate.getText().toString().isEmpty()) {
            val date1 =
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year)
                    myCalendar.set(Calendar.MONTH, monthOfYear)
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateLabel(lblstartdate)
                }

            val datePickerDialog = DatePickerDialog(
                activity!!,
                R.style.ParentCalendarTheme,
                date1,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
            datePickerDialog.show()

        } else {
            DateAlert("Do You Want to Change FROM Date")
        }
    }

    fun updateLabel(txt_date: TextView) {
        val myFormat = "dd/MM/yyyy" //In which you need put here
        val sdf =
            SimpleDateFormat(myFormat, Locale.US)
        txt_date.text = sdf.format(myCalendar.time)
        FromDate = txt_date.text.toString()
        UtilConstants.LeaveFrom = FromDate

    }


    private fun updateLabel1(txt_date: TextView) {
        val myFormat = "dd/MM/yyyy"
        val sdf =
            SimpleDateFormat(myFormat, Locale.FRANCE)
        txt_date.text = sdf.format(myCalendar.time)
        Todate = txt_date.text.toString()
        UtilConstants.LeaveTo = Todate


    }

    fun getLeaveType() {


        val mobileNumber = Util_shared_preferences.getMobileNumber(activity)
        val loginToken = Util_shared_preferences.getLoginToken(activity)
        val schoolID = Util_shared_preferences.getStudentSchoolID(activity)
        val StudentID = Util_shared_preferences.getStudentID(activity)
        val ClassID = Util_shared_preferences.getStudentClassID(activity)
        val SectionID = Util_shared_preferences.getStudentSectionID(activity)


        val jsonObject = JsonObject()
        jsonObject.addProperty(ApiRequestValues.LOGIN_TOKEN, loginToken)
        jsonObject.addProperty(ApiRequestValues.MOBILE_NUMBER, mobileNumber)
        jsonObject.addProperty(ApiRequestValues.SCHOOL_ID, schoolID)
        jsonObject.addProperty(ApiRequestValues.STUDENT_ID, StudentID)
        jsonObject.addProperty(ApiRequestValues.CLASS_ID, ClassID)
        jsonObject.addProperty(ApiRequestValues.SECTION_ID, SectionID)

        GifLoading.loading(activity, false)
        var apiInterface: ApiInterface = APIClient.getApiClient()!!.create(ApiInterface::class.java)
        apiInterface.getLeaveType(jsonObject)!!
            .enqueue(object : retrofit2.Callback<GetLeaveType?> {
                override fun onResponse(
                    call: Call<GetLeaveType?>?,
                    response: Response<GetLeaveType?>?
                ) {
                    try {
                        GifLoading.loading(activity, false)
                        val responseBody = response?.body()
                        val gson = Gson()
                        Log.d("getLeavetype", gson.toJson(response))
                        leavetypeList.clear()
                        if (response?.code() == 200) {
                            if (responseBody?.status == 1) {
                                leavetypeList = responseBody.data as ArrayList<LeaveTypeData>

                                leaveType()
                            }
                        } else if (response?.code() == 400 || response?.code() == 500) {
                            val errorResponseBody = Gson().fromJson(
                                response.errorBody()?.charStream(),
                                StatusMessageResponse::class.java
                            )
                            UtilConstants.handleErrorResponse(
                                activity,
                                response.code(),
                                errorResponseBody
                            )
                        } else {
                            UtilConstants.normalToast(
                                activity,
                                activity?.getString(R.string.Service_unavailable)
                            )
                        }
                    } catch (e: Exception) {
                        Log.d("Exception", e.toString())
                    }
                }

                override fun onFailure(call: Call<GetLeaveType?>?, t: Throwable?) {
                    Log.d("Failure", t.toString())
                    UtilConstants.normalToast(activity, t.toString())
                }
            })
    }

    private fun leaveType() {
        leaveNames.clear()
        leavetypeList.forEach {
            leaveNames.add(it.leave_type)
        }
        if (LeaveSpin != null) {
            val adapter = ArrayAdapter(activity!!, R.layout.school_spin_title, leaveNames)
            adapter.setDropDownViewResource(R.layout.school_dropdown)
            LeaveSpin.adapter = adapter
            LeaveSpin.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    UtilConstants.SelectedLeaveID = leavetypeList.get(position).id.toString()
                    Log.d("testLeaveType", UtilConstants.SelectedLeaveID!!)
                    if (SelectedLeaveID.equals("0")) {
                        edOtherDescription.visibility = View.VISIBLE
                        lblOthers.visibility = View.VISIBLE
                    } else {
                        edOtherDescription.visibility = View.GONE
                        lblOthers.visibility = View.GONE

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
    }

    //  private fun alert(msg: String) {
//        if (context != null) {
//            val dlg = android.app.AlertDialog.Builder(context)
//            dlg.setTitle("Alert")
//            dlg.setMessage(msg)
//            dlg.setPositiveButton(
//                "OK"
//            ) { dialog, which ->
//                dlg.setCancelable(true)
//                //                finish();
//            }
//            dlg.setCancelable(false)
//            dlg.create()
//            dlg.show()
//        }
//    }


    private fun DateAlert(text: String) {
        if (context != null) {
            val dlg = android.app.AlertDialog.Builder(context)
            dlg.setTitle("Alert")
            dlg.setMessage(text)
            dlg.setPositiveButton(
                "Yes"
            ) { dialog, which ->
                lblenddate.setText("")
                lblenddate.setHint("To")
                lblenddate.setHintTextColor(Color.parseColor("#F1DBD8D8"))
                lblstartdate.setHintTextColor(Color.parseColor("#F1DBD8D8"))
                val date1 =
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth -> //
                        myCalendar[Calendar.YEAR] = year
                        myCalendar[Calendar.MONTH] = monthOfYear
                        myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                        updateLabel(lblstartdate)
                    }
                val datePickerDialog = DatePickerDialog(
                    activity!!,
                    R.style.ParentCalendarTheme,
                    date1,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                )

                //                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, date1, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
                datePickerDialog.show()
            }
            dlg.setNegativeButton(
                "No"
            ) { dialog, which -> dlg.setCancelable(true) }
            dlg.setCancelable(false)
            dlg.create()
            dlg.show()
        }
    }

    private fun alert(s: String) {
        if (context != null) {
            val dlg = android.app.AlertDialog.Builder(context)
            dlg.setTitle("Alert")
            dlg.setMessage(s)
            dlg.setPositiveButton(
                "OK"
            ) { dialog, which ->
                dlg.setCancelable(true)
                //                finish();
            }
            dlg.setCancelable(false)
            dlg.create()
            dlg.show()
        }

    }

}
