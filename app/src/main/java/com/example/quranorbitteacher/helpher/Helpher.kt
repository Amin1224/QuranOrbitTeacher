package com.example.quranorbitteacher.helpher

import android.app.Activity
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity
import java.text.SimpleDateFormat
import java.util.*

class Helpher {
    companion object {
        fun isEmailValid(trim: String?): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(trim).matches()
        }

        fun date(current:(String)->Unit){
            val sdf = SimpleDateFormat("dd/M/yyyy")
            val currentDate = sdf.format(Date())
            current.invoke(currentDate)
        }
        fun time(time:(String)->Unit){
            val sdf = SimpleDateFormat("hh:mm:ss")
            val currentTime = sdf.format(Date())
            time.invoke(currentTime)
        }

        fun hideKeyBoard(requireActivity: FragmentActivity) {
            val imm = requireActivity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager?
            imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }

    }
}