package com.example.quranorbitteacher.fragments.schedule_call

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.csr_dailog.CsrDailog
import com.example.quranorbitteacher.databinding.FragmentScheduleCallBinding
import com.example.quranorbitteacher.model.ScheduleCall
import com.example.quranorbitteacher.shared_pref.prefs
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class Schedule_Call : Fragment() {

    companion object {
        fun newInstance() = Schedule_Call()
    }

    private lateinit var viewModel: ScheduleCallViewModel
    private lateinit var binding: FragmentScheduleCallBinding
    private var counter: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScheduleCallBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleCallViewModel::class.java)
        onclick()
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        binding.calender.setOnClickListener {
            DatePickerDialog(
                requireContext(), datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }

    }

    private fun onclick() {
        binding.callerName.isSingleLine = true
        binding.callerMobileNo.isSingleLine = true
        binding.callerCountryText.isSingleLine = true
        binding.callerRemarks.isSingleLine = true
        binding.callerSkypeId.isSingleLine = true
        binding.interestedCourseText.isSingleLine = true

        binding.backPress.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.submitCallerSchedule.setOnClickListener {
            checkInput()
        }
        binding.interestedMenu.setOnClickListener {
            CsrDailog.requiredCourse(requireContext(), binding.interestedMenu) {
                binding.interestedCourseText.text = it
            }
        }
        binding.country.setOnClickListener {
            CsrDailog.requiredCountry(requireContext(), binding.country) {
                binding.callerCountryText.text = it
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.callerDate.setText(sdf.format(myCalendar.time))
    }

    private fun checkInput() {
        if (TextUtils.isEmpty(binding.callerName.text.toString().trim())) {
            binding.callerName.requestFocus()
            binding.callerName.error = "Enter caller name"
        } else if (TextUtils.isEmpty(binding.callerMobileNo.text.toString().trim())) {
            binding.callerMobileNo.requestFocus()
            binding.callerMobileNo.error = "Enter caller Mobile number"
        } else if (TextUtils.isEmpty(binding.callerSkypeId.text.toString().trim())) {
            binding.callerSkypeId.requestFocus()
            binding.callerSkypeId.error = "Enter caller caller Skype Id"
        } else if (TextUtils.isEmpty(binding.callerDate.text.toString().trim())) {
            binding.callerSkypeId.requestFocus()
            binding.callerSkypeId.error = "Enter call Date time"
        } else if (TextUtils.isEmpty(binding.callerCountryText.text.toString().trim())) {
            binding.callerCountryText.requestFocus()
            binding.callerCountryText.error = "Enter caller caller Country"
        } else if (TextUtils.isEmpty(binding.callerRemarks.text.toString().trim())) {
            binding.callerRemarks.requestFocus()
            binding.callerRemarks.error = "Enter caller caller remarks"
        } else if (TextUtils.isEmpty(binding.interestedCourseText.text.toString().trim())) {
            binding.interestedCourseText.requestFocus()
            binding.interestedCourseText.error = "Enter caller caller remarks"
        } else {
            continueRegistration()
        }
    }

    private fun continueRegistration() {
        val key = FirebaseDatabase.getInstance().getReference("CallerSchedule").push().key
        val callSchedule = ScheduleCall(
            binding.callerName.text.toString(),
            binding.callerMobileNo.text.toString(),
            binding.callerDate.text.toString(),
            binding.callerSkypeId.text.toString(),
            binding.callerCountryText.text.toString(),
            binding.callerRemarks.text.toString(),
            Common.CURRENT_USERS.uid,
            binding.interestedCourseText.text.toString()
        )
        Log.d("mTag", "uploadDailyReport: $key")
        if (key != null) {

            FirebaseDatabase.getInstance().getReference("CallerSchedule")
                .child(key)
                .setValue(callSchedule)
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "fail to upload", Toast.LENGTH_SHORT).show();

                }
                .addOnCompleteListener {
                    if (it.isSuccessful()) {
                        Toast.makeText(
                            requireContext(),
                            "Successfully Uploaded",
                            Toast.LENGTH_SHORT
                        ).show()
                        counter++
                        prefs.schuduleCall = counter
                        requireActivity().onBackPressed()

                    } else {
                        Toast.makeText(
                            requireContext(),
                            it.getException()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

    }

}