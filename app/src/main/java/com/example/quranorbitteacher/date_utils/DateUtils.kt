package com.example.quranorbitteacher.date_utils

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {
    companion object{

        @RequiresApi(Build.VERSION_CODES.N)
        fun datePicker(requireContext: Context, date: ImageView,dateShow: (String) -> Unit) {

            val myCalendar= Calendar.getInstance()
            val datePicker= DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
                myCalendar.set(Calendar.YEAR,year)
                myCalendar.set(Calendar.MONTH,month)
                myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
                updateLable(myCalendar,dateShow)
            }




        }


        @RequiresApi(Build.VERSION_CODES.N)
        private fun updateLable(myCalendar: Calendar,date: (String) -> Unit) {
            val myFormat="dd-MM-yyyy"
            val sdf= SimpleDateFormat(myFormat, Locale.UK)
            val date=sdf.format(myCalendar.time)
        }
    }
}