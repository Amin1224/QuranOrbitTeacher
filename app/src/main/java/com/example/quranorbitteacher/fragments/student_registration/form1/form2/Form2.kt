package com.example.quranorbitteacher.fragments.student_registration.form1.form2

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.csr_dailog.CsrDailog
import com.example.quranorbitteacher.databinding.FragmentForm2Binding
import com.example.quranorbitteacher.fragments.csr.CSRViewModel
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.model.StudentForm2
import com.example.quranorbitteacher.registration_dailog.RegistrationDailog
import java.text.SimpleDateFormat
import java.util.*

class Form2 : Fragment() {

    companion object {
        fun newInstance() = Form2()
    }

    private lateinit var viewModel: CSRViewModel
    private lateinit var binding: FragmentForm2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForm2Binding.inflate(layoutInflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CSRViewModel::class.java)
        onclick()
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLable(myCalendar)
        }
        binding.trailDateMenu.setOnClickListener {
            DatePickerDialog(
                requireContext(), datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH),
            ).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateLable(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.UK)
        binding.trailDate.setText(sdf.format(myCalendar.time))


    }

    private fun onclick() {
        binding.skypeIdTrial.isSingleLine = true
        binding.contactNumber.isSingleLine = true
        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_form2_to_profile2)
        }

        binding.next.setOnClickListener {
            checkInput()
        }
        binding.backPress.setOnClickListener {
            requireActivity().onBackPressed()
        }
        Helpher.date {
            binding.creationDate.text = it
        }

        Helpher.time {
            binding.creationTime.text = it
        }

        binding.requiredTeacherMenu.setOnClickListener {
            RegistrationDailog.genderDailog(requireContext(), binding.requiredTeacherMenu) {
                binding.requiredTeacher.text = it
            }
        }
        binding.currencyMenu.setOnClickListener {
            CsrDailog.requiredCurrency(requireContext(), binding.currencyMenu) {
                binding.currency.text = it
            }
        }
    }

    private fun checkInput() {
        if (TextUtils.isEmpty(binding.requiredTeacher.text.toString())) {
            binding.requiredTeacher.requestFocus()
            binding.requiredTeacher.error = "Select teacher"
        } else if (TextUtils.isEmpty(binding.currency.text.toString().trim())) {
            binding.currency.requestFocus()
            binding.currency.error = "Select currency"
        } else if (TextUtils.isEmpty(binding.trailDate.text.toString().trim())) {
            binding.trailDate.requestFocus()
            binding.trailDate.error = "Select your trial date"
        } else if (TextUtils.isEmpty(binding.skypeIdTrial.text.toString().trim())) {
            binding.skypeIdTrial.requestFocus()
            binding.skypeIdTrial.error = "Enter your Skype ID"
        } else if (TextUtils.isEmpty(binding.contactNumber.text.toString().trim())) {
            binding.contactNumber.requestFocus()
            binding.contactNumber.error = "Enter your contact Number"
        } else {
            moveToNext()
        }
    }

    private fun moveToNext() {
        viewModel.studentForm2 = StudentForm2(
            "",
            binding.currency.text.toString(),
            binding.trailDate.text.toString(),
            binding.skypeIdTrial.text.toString(),
            binding.contactNumber.text.toString()

        )
        findNavController().navigate(R.id.action_form2_to_form3)
    }

}