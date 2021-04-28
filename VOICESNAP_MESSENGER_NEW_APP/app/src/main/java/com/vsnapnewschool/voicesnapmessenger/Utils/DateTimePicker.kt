package com.vsnapnewschool.voicesnapmessenger.Utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.TimePicker
import com.vsnapnewschool.voicesnapmessenger.R
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*

class DateTimePicker(var context: Context?, var dateset: TextView?, var timeset: TextView?,var lblAmp:TextView) {
    val myCalendar = Calendar.getInstance()

    val date1: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar[Calendar.YEAR] = year
            myCalendar[Calendar.MONTH] = monthOfYear
            myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth

        }

    val datePickerDialog = DatePickerDialog(context!!, R.style.TeacherDatePicker, date1, myCalendar[Calendar.YEAR], myCalendar[Calendar.MONTH], myCalendar[Calendar.DAY_OF_MONTH])
    init {
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setOnClickListener({
            val myFormat = "dd MMM yyyy" //In which you need put here
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            dateset!!.text = sdf.format(myCalendar.time)
            Log.d("testdate",sdf.toString())
            Log.d("sdf",sdf.format(myCalendar.time))
            datePickerDialog.dismiss()
            val hourOfDay: Int
            val minute: Int

            val c = Calendar.getInstance()
            hourOfDay = c[Calendar.HOUR_OF_DAY]
            minute = c[Calendar.MINUTE]

            val dpd = TimePickerDialog(
                context, TimePickerDialog.OnTimeSetListener { timePicker: TimePicker?, hourOfDay1: Int, minute1: Int ->

                    lblAmp.visibility= View.VISIBLE
                    val time = Time(hourOfDay1, minute1, 0)
                    val simpleDateFormat = SimpleDateFormat("hh:mm aa", Locale.getDefault())
                    val s = simpleDateFormat.format(time)
                    timeset!!.setText(s)
                },
                hourOfDay,
                minute,
                false
            )

            dpd.show()
        })
    }
}