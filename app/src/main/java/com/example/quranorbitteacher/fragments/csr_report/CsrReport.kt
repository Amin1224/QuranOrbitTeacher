package com.example.quranorbitteacher.fragments.csr_report

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.quranorbitteacher.databinding.FragmentCsrReportBinding
import com.example.quranorbitteacher.model.DailyReport
import com.example.quranorbitteacher.shared_pref.SharedPref
import com.example.quranorbitteacher.shared_pref.prefs
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class CsrReport : Fragment() {

    private lateinit var viewModel: CsrReportViewModel
    private lateinit var binding: FragmentCsrReportBinding
    private lateinit var currentDate: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCsrReportBinding.inflate(layoutInflater, container, false)
        sharedPreferenceEmpty()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CsrReportViewModel::class.java)
        var currentNumber = prefs.dialCalls
        binding.dailCallQuantity.text="$currentNumber"
        var attendedCall= prefs.attendedCalls
        binding.attendeCallQuantity.text="$attendedCall"
        var scheduleCallBook= prefs.schuduleCall
        binding.shiftCallQuantity.text="$scheduleCallBook"
        var trialBook= prefs.trialBooking
        binding.shiftTrialQuantity.text="$trialBook"

        var dropCall=currentNumber-attendedCall
        binding.droppedCallQuantity.text="$dropCall"
        val campare = SimpleDateFormat("hh:mm",Locale.getDefault())
        val a=campare.format(Date())
        Log.i("MTAG", "outer: $a")

        if (a.toString()=="07:48"){
            Log.i("MTAG", "onViewCreated: $a")
            prefs.dialCalls=0
            prefs.attendedCalls=0
            prefs.trialBooking=0
            prefs.schuduleCall=0
        }

        binding.sendReport.setOnClickListener {
            checkInput()
        }

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss",Locale.getDefault())
        currentDate = sdf.format(Date())
        binding.dateShow.setText(currentDate)
    }

    private fun sharedPreferenceEmpty() {

    }

    private fun checkInput() {

        if (TextUtils.isEmpty(binding.dailCallQuantity.text.toString().trim())) {
            binding.dailCallQuantity.requestFocus()
            binding.dailCallQuantity.error = "Enter Dail call"
        } else if (TextUtils.isEmpty(binding.attendeCallQuantity.text.toString().trim())) {
            binding.attendeCallQuantity.requestFocus()
            binding.attendeCallQuantity.error = "Enter attended call"
        } else if (TextUtils.isEmpty(binding.droppedCallQuantity.text.toString().trim())) {
            binding.droppedCallQuantity.requestFocus()
            binding.droppedCallQuantity.error = "Enter dropped call"
        } else if (TextUtils.isEmpty(binding.shiftCallQuantity.text.toString().trim())) {
            binding.shiftCallQuantity.requestFocus()
            binding.shiftCallQuantity.error = "Enter shifted call"
        } else {
            Toast.makeText(requireContext(), "upload start", Toast.LENGTH_SHORT).show()
            uploadDailyReport()
        }
    }

    private fun uploadDailyReport() {
        val key = FirebaseDatabase.getInstance().getReference("CallsReport").push().key
        val reportForm = DailyReport(
            currentDate,
            binding.dailCallQuantity.text.toString().trim(),
            binding.attendeCallQuantity.text.toString().trim(),
            binding.droppedCallQuantity.text.toString().trim(),
            binding.shiftCallQuantity.text.toString().trim(),
            ""

        )

        Toast.makeText(requireContext(), "key was generated", Toast.LENGTH_SHORT).show()

        Log.d("mTag", "uploadDailyReport: $key")
        if (key != null) {
            Toast.makeText(requireContext(), "key was generated", Toast.LENGTH_SHORT).show()
            FirebaseDatabase.getInstance().getReference("CallsReport")
                .child(key)
                .setValue(reportForm)
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

                    } else {
                        Toast.makeText(
                            requireContext(),
                            it.getException()?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(requireContext(), "null key", Toast.LENGTH_SHORT).show()
        }

    }

}