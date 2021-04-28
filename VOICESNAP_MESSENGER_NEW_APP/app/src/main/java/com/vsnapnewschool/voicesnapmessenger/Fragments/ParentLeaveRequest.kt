package com.vsnapnewschool.voicesnapmessenger.Fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.vsnapnewschool.voicesnapmessenger.Activities.BaseActivity
import com.vsnapnewschool.voicesnapmessenger.R
import kotlinx.android.synthetic.main.leave_request_apply.*
import java.util.*


class ParentLeaveRequest: Fragment(),View.OnClickListener {
    var cal = Calendar.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {return inflater?.inflate(
        R.layout.leave_request_scroll,
        container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lblstartdate.setHint("From")
        lblenddate.setHint("To")
        lblstartdate?.setOnClickListener(this)
        lblenddate?.setOnClickListener(this)
        btndiscard?.setOnClickListener(this)
        btnApply?.setOnClickListener(this)

    }
    override fun onClick(v: View?) {

        when(v!!.id) {
            R.id.lblstartdate -> {
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)

                if (lblenddate.getText().toString().isEmpty()) {
                    activity?.let { (activity as BaseActivity?)!!.PickDate(it,lblstartdate,false,0) }
                } else {
                    alert("Do You Want to Change FROM Date")
                }
            }
            R.id.lblenddate -> {
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)

                if (lblstartdate.getText().toString().isEmpty()) {
                    alertTo("Please Choose From Date")
                } else {

                    activity?.let { (activity as BaseActivity?)!!.EndDate(it,lblenddate) }

                }
            }
            R.id.btndiscard -> {
                (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)

                activity?.onBackPressed()

            }R.id.btnApply->{
            (activity as BaseActivity?)!!. HideKeyboard_Fragment(context as Activity?)


        }
        }
    }


    private fun alert(s: String) {

        val dialogBuilder = context?.let { AlertDialog.Builder(it) }

        dialogBuilder?.setMessage(s)
        dialogBuilder ?.setCancelable(false)
        dialogBuilder?.setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            lblenddate.setText("")
                lblenddate.setHint("To")

                lblenddate.setHintTextColor(Color.parseColor("#F1DBD8D8"))
                lblstartdate.setHintTextColor(Color.parseColor("#F1DBD8D8"))

                val date2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, monthOfYear)
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    (activity as BaseActivity?)!!.fromDate(lblstartdate)
                }
                val datePickerDialog =
                    context?.let {
                        DatePickerDialog(it, R.style.ParentCalendarTheme, date2,
                            cal.get(Calendar.YEAR),
                            cal.get(Calendar.MONTH),
                            cal.get(Calendar.DAY_OF_MONTH)
                        )
                    }
                datePickerDialog?.datePicker?.minDate = System.currentTimeMillis() - 1000
                datePickerDialog?.show()
            })
            ?.setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder?.create()
        alert?.setTitle("Alert")
        alert?.show()

    }
    private fun alertTo(s: String) {
        val dialogBuilder = context?.let { AlertDialog.Builder(it) }
        dialogBuilder?.setMessage(s)?.setCancelable(false)?.setPositiveButton("Ok", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = dialogBuilder?.create()
        alert?.setTitle("Alert")
        alert?.show()
    }
}
