package com.example.quranorbitteacher.fragments.student_registration.form1

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
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.csr_dailog.CsrDailog
import com.example.quranorbitteacher.databinding.FragmentForm1Binding
import com.example.quranorbitteacher.fragments.csr.CSRViewModel
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.model.StudentForm1
import java.text.SimpleDateFormat
import java.util.*

class Form1 : Fragment() {

    companion object {
        fun newInstance() = Form1()
    }

    private lateinit var viewModel: CSRViewModel
    private lateinit var binding: FragmentForm1Binding
    lateinit var gender: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForm1Binding.inflate(layoutInflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CSRViewModel::class.java)

        val myCalendar= Calendar.getInstance()
        val datePicker= DatePickerDialog.OnDateSetListener{ view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR,year)
            myCalendar.set(Calendar.MONTH,month)
            myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateLable(myCalendar)
        }
        binding.dateOfBirthMenu.setOnClickListener {
            DatePickerDialog(requireContext(),datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH),).show()
        }

        onclick()

    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateLable(myCalendar: Calendar) {
        val myFormat="dd-MM-yyyy"
        val sdf= SimpleDateFormat(myFormat, Locale.UK)
        binding.ageText.setText(sdf.format(myCalendar.time))
    }

    private fun checkGender() {
        binding.male.setOnCheckedChangeListener { compoundButton, state ->
            if (state) {
                gender = "Male"
            }
        }
        binding.female.setOnCheckedChangeListener { compoundButton, state ->
            if (state) {
                gender = "Female"
            }
        }
    }

    private fun onclick() {
        binding.name.isSingleLine = true
        binding.lastName.isSingleLine = true
        binding.fatherName.isSingleLine = true
        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_form1_to_profile2)
        }
        binding.backPress.setOnClickListener {
            requireActivity().onBackPressed()
        }
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_form1_to_form2)
        }
        binding.next.setOnClickListener {
            checkInput()
        }

            Helpher.date {
                binding.creationDate.text=it
                Log.d("kTAG", "onclick: $it")
            }

            Helpher.time {
                binding.creationTime.text=it
            }

        checkGender()
        binding.countryMenu.setOnClickListener {
            checkCountry()
        }


    }

    private fun checkCountry() {
        CsrDailog.requiredCountry(requireContext(),binding.countryMenu){
            binding.countrySetText.text=it
        }
    }

    private fun checkInput() {
        if (TextUtils.isEmpty(binding.name.text.toString().trim())) {
            binding.name.requestFocus()
            binding.name.error = "Enter first name"
        } else if (TextUtils.isEmpty(binding.lastName.text.toString().trim())) {
            binding.lastName.requestFocus()
            binding.lastName.error = "Enter last name"
        } else if (TextUtils.isEmpty(binding.fatherName.text.toString().trim())) {
            binding.fatherName.requestFocus()
            binding.fatherName.error = "Enter Father name"
        } else if (TextUtils.isEmpty(binding.ageText.text.toString().trim())) {
            binding.ageText.requestFocus()
            binding.ageText.error = "Select your age"
        } else if (TextUtils.isEmpty(gender)) {
            binding.lastName.requestFocus()
            Toast.makeText(requireContext(), "Select your gender", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(binding.countrySetText.text.toString().trim())) {
            binding.countrySetText.requestFocus()
            binding.countrySetText.error = "Enter country name"
        } else {
            moveToNext()
        }
    }

    private fun moveToNext() {
        viewModel.studentForm1=StudentForm1(
            binding.name.text.toString(),
            binding.lastName.text.toString(),
            binding.fatherName.text.toString(),
            binding.ageText.text.toString(),
            gender,
            binding.countrySetText.text.toString(),

        )
        findNavController().navigate(R.id.action_form1_to_form2)
    }

}