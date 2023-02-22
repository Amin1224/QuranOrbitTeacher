package com.example.quranorbitteacher.fragments.registration_csr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.FragmentRegistrationBinding
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.model.RegistrationCsr
import com.example.quranorbitteacher.registration_dailog.RegistrationDailog

class Registration : Fragment() {

    companion object {
        fun newInstance() = Registration()
    }

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var gender:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RegistrationViewModel::class.java)

        //   viewModel.formFirst(RegistrationCsr("name","address"))


        // Log.d("viewModel",viewModel.formFirst.name.toString())

        onclick()
    }

    private fun onclick() {
        binding.name.isSingleLine = true
        binding.lastName.isSingleLine = true
        binding.fatherName.isSingleLine = true
        binding.email.isSingleLine = true
        binding.next.setOnClickListener {
            checkInput()
        }
        binding.genderMenu.setOnClickListener {
            Helpher.hideKeyBoard(requireActivity())
            RegistrationDailog.
                    genderDailog(requireContext(), binding.genderMenu) {
                        binding.genderText.text = gender
                    }
        }
        binding.genderMenu.setOnClickListener {
            RegistrationDailog.genderDailog(requireContext(),binding.genderMenu){
                binding.genderText.text=it
                gender=it
            }

        }
        binding.accountTypeMenu.setOnClickListener {
            RegistrationDailog.accountType(requireContext(),binding.accountTypeMenu){
                binding.accountTypeSetText.text=it
            }
        }
    }


    private fun checkInput() {
        if (TextUtils.isEmpty(binding.name.text.toString().trim())) {
            binding.name.requestFocus()
            binding.name.error = "Enter your first name"
        } else if (TextUtils.isEmpty(binding.lastName.text.toString().trim())) {
            binding.lastName.requestFocus()
            binding.lastName.error = "Enter your last name"
        }
            else if (TextUtils.isEmpty(binding.mobileNo.text.toString().trim())) {
                binding.mobileNo.requestFocus()
                binding.mobileNo.error = "Enter your mobile no"
            } else if (TextUtils.isEmpty(binding.fatherName.text.toString().trim())) {
                binding.fatherName.requestFocus()
                binding.fatherName.error = "Enter your father name"
            } else if (TextUtils.isEmpty(binding.fatherNo.text.toString().trim())) {
                binding.fatherNo.requestFocus()
                binding.fatherNo.error = "Enter your Father mobile no"
            } else if (!Helpher.isEmailValid(binding.email.text.toString().trim())) {
                binding.email.requestFocus()
                binding.email.error = "Enter your email address"
            }else if (TextUtils.isEmpty(binding.genderText.text)) {
            binding.genderText.requestFocus()
            binding.genderText.error = "Enter your email address"
        } else if (TextUtils.isEmpty(binding.accountTypeSetText.text)) {
            binding.accountTypeSetText.requestFocus()
            binding.accountTypeSetText.error = "Select your Account Type"
        } else {

                continueRegistration()
            }
        }

        private fun continueRegistration() {
            viewModel.formFirst = RegistrationCsr(
                binding.name.text.toString(),
                binding.lastName.text.toString(),
                binding.mobileNo.text.toString(),
                binding.fatherName.text.toString(),
                binding.fatherNo.text.toString(),
                binding.email.text.toString(),
                gender,
                binding.accountTypeSetText.text.toString()
            )
            findNavController().navigate(R.id.action_registration2_to_scoundForm2)

        }

    }