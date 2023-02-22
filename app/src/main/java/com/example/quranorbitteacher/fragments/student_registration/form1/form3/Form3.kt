package com.example.quranorbitteacher.fragments.student_registration.form1.form3

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.csr_dailog.CsrDailog
import com.example.quranorbitteacher.databinding.FragmentForm3Binding
import com.example.quranorbitteacher.fragments.csr.CSRViewModel
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.model.StudentForm3
import com.example.quranorbitteacher.registration_dailog.RegistrationDailog
import com.example.quranorbitteacher.shared_pref.prefs
import com.google.firebase.database.FirebaseDatabase

class Form3 : Fragment() {

    companion object {
        fun newInstance() = Form3()
    }

    private lateinit var viewModel: CSRViewModel
    private lateinit var binding: FragmentForm3Binding
    lateinit var days: String
    lateinit var timeBulder: String
    lateinit var timeCounter: String
    private var counter:Int=0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForm3Binding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CSRViewModel::class.java)

        onclick()

    }

    private fun onclick() {


        binding.profile.setOnClickListener {
            findNavController().navigate(R.id.action_form3_to_profile2)
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
        binding.requiredLanguageMenu.setOnClickListener {
            RegistrationDailog.language(requireContext(), binding.requiredLanguageMenu) {
                binding.requiredLanguage.text = it
            }
        }
        binding.am.setOnClickListener {
            timeCounter = "Am"
            binding.am.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
            binding.pm.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
        }
        binding.pm.setOnClickListener {
            timeCounter = "Pm"
            binding.pm.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
            binding.am.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
        }
        binding.requiredCourseMenu.setOnClickListener {
            CsrDailog.requiredCourse(requireContext(), binding.requiredCourseMenu) {
                binding.requiredCourse.text = it
            }
        }
        binding.classDurationMenu.setOnClickListener {
            CsrDailog.classDuration(requireContext(), binding.classDurationMenu) {
                binding.classDuration.text = it
            }
        }

        binding.mon.setOnClickListener {
            days = "Mon"
            binding.mon.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
            binding.tue.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.wed.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.thu.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.fri.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )

        }
        binding.tue.setOnClickListener {
            days = "Tue"
            binding.mon.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.tue.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
            binding.wed.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.thu.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.fri.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
        }
        binding.wed.setOnClickListener {
            days = "Wed"
            binding.mon.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.tue.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.wed.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
            binding.thu.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.fri.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
        }
        binding.thu.setOnClickListener {
            days = "Thu"
            binding.mon.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.tue.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.wed.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.thu.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
            binding.fri.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
        }
        binding.fri.setOnClickListener {
            days = "Fri"
            binding.mon.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.tue.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.wed.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.thu.setTextColor(
                ResourcesCompat.getColor(
                    resources,
                    R.color.primary_color,
                    null
                )
            )
            binding.fri.setTextColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
        }

    }


    private fun checkInput() {
        val stringBulder = StringBuilder()
        val fullTime =
            stringBulder.append(
                binding.ed1.text.toString()).append(binding.ed2.text.toString())
                .append(":").append(binding.ed3.text.toString())
                .append(binding.ed4.text.toString()).append(timeCounter)

        if (TextUtils.isEmpty(binding.requiredLanguage.text.toString().trim())) {
            binding.requiredLanguage.requestFocus()
            binding.requiredLanguage.error = "Select your language"
        } else if (TextUtils.isEmpty(binding.classDuration.text.toString().trim())) {
            binding.classDuration.requestFocus()
            binding.classDuration.error = "Select your class duration"
        }else if (binding.ed1.text.toString()>"3" && binding.ed2.text.toString()>"9") {
            binding.classDuration.requestFocus()
            Toast.makeText(requireContext(), "Select exit time", Toast.LENGTH_SHORT).show()
        }  else if (TextUtils.isEmpty(binding.requiredCourse.text.toString().trim())) {
            binding.requiredCourse.requestFocus()
            binding.requiredCourse.error = "Select your required Course"
        } else if (TextUtils.isEmpty(days)) {
            binding.fri.requestFocus()
            binding.fri.error = "Select your day"
        } else if (TextUtils.isEmpty(fullTime)) {
            binding.ed1.requestFocus()
            binding.ed1.error = "Enter start time"
        } else {
            binding.progressBar.visibility = View.VISIBLE
            binding.next.visibility = View.INVISIBLE
            updateUI(fullTime)
        }
    }

    private fun updateUI(fullTime: java.lang.StringBuilder) {


        val key = FirebaseDatabase.getInstance().getReference("STUDENT_REGISTER").push().key
        val studentData = StudentForm3(

            viewModel.studentForm1.name,
            viewModel.studentForm1.lName,
            viewModel.studentForm1.faherName,
            viewModel.studentForm1.age,
            viewModel.studentForm1.gender,
            viewModel.studentForm1.country,
            viewModel.studentForm2.teacher,
            viewModel.studentForm2.currency,
            viewModel.studentForm2.trialDate,
            viewModel.studentForm2.skypeId,
            viewModel.studentForm2.contactNumber,
            binding.requiredLanguage.text.toString(),
            fullTime.toString(),
            binding.classDuration.text.toString(),
            days,
            binding.requiredCourse.text.toString(),
            Common.CURRENT_USERS.uid,
            Common.CURRENT_USERS.name,
            binding.creationDate.text.toString(),
            key,
            "",
            "",
            "",
            ""
        )
        if (key != null) {


            FirebaseDatabase.getInstance().getReference("STUDENT_REGISTER")
                .child(key)
                .setValue(studentData)
                .addOnFailureListener {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "Fail to upload", Toast.LENGTH_SHORT).show()
                }
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "successfully Uploaded",
                            Toast.LENGTH_SHORT
                        ).show()
                        counter++
                        prefs.trialBooking=counter
                        findNavController().navigate(R.id.action_form3_to_CSR)
                    } else {
                        binding.next.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
    }
}



