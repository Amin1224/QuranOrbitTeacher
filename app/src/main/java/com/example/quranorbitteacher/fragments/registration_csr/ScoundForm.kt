package com.example.quranorbitteacher.fragments.registration_csr

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.quranorbitteacher.activities.CsrFloorManager
import com.example.quranorbitteacher.activities.MainActivity
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.databinding.FragmentScoundFormBinding
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.image_utils.ImageUtils
import com.example.quranorbitteacher.model.FullRegistration
import com.example.quranorbitteacher.registration_dailog.RegistrationDailog
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ScoundForm : Fragment() {


    private lateinit var viewModel: RegistrationViewModel
    private lateinit var binding: FragmentScoundFormBinding
    private lateinit var education: String
    private lateinit var shiftTime: String
    lateinit var imageUri: Uri
    lateinit var frontPage: String
    lateinit var backPage: String
    var b: Boolean = false


    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            if (b == true) {
                binding.frontPage.setImageURI(null)
                binding.frontPage.setImageURI(imageUri)
                ImageUtils.uploadImage(requireContext(), imageUri) {
                    frontPage = it
                }
            } else {
                binding.backPage.setImageURI(null)
                binding.backPage.setImageURI(imageUri)
                ImageUtils.uploadImage(requireContext(), imageUri) {
                    backPage = it
                }
            }
        }
    }

    private val mAuth by lazy {
        Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentScoundFormBinding.inflate(layoutInflater, container, false)
        onclick()


        return binding.root
    }

    private fun createImageUri(): Uri? {

        val back = File(requireActivity().applicationContext.filesDir, "camera_photo_png")
        return FileProvider.getUriForFile(
            requireActivity().applicationContext,
            "com.example.quranorbitteacher.QuranOrbitTeacher.FileProvider",
            back
        )
    }

    private fun onclick() {

        binding.password.isSingleLine = true
        binding.cnicNo.isSingleLine = true
        binding.address.isSingleLine = true

        binding.frontPage.setOnClickListener {
            b = true
            imageUri = createImageUri()!!
            contract.launch(imageUri)
            Helpher.hideKeyBoard(requireActivity())

        }
        binding.backPage.setOnClickListener {
            b = false
            imageUri = createImageUri()!!
            contract.launch(imageUri)
            Helpher.hideKeyBoard(requireActivity())

        }

        binding.shiftMenu.setOnClickListener {
            Helpher.hideKeyBoard(requireActivity())
            RegistrationDailog.shiftDailog(requireContext(), binding.shiftSetText) {
                binding.shiftSetText.text = it
                shiftTime = it
            }
        }
        binding.qualificationMenu.setOnClickListener {
            Helpher.hideKeyBoard(requireActivity())
            RegistrationDailog.qualification(requireContext(), binding.qualification) {
                binding.qualificationSetText.text = it
                education = it
            }
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RegistrationViewModel::class.java)
        binding.next.setOnClickListener {
            checkInput()
        }

        Log.d("viewModel", viewModel.formFirst.toString())

    }

    private fun checkInput() {
        if (TextUtils.isEmpty(binding.qualificationSetText.text.toString().trim())) {
            binding.qualificationSetText.requestFocus()
            binding.qualificationSetText.error = "Enter your name"
        } else if (TextUtils.isEmpty(binding.shiftSetText.text.toString().trim())) {
            binding.shiftSetText.requestFocus()
            binding.shiftSetText.error = "Enter your mobile no"
        } else if (TextUtils.isEmpty(binding.password.text.toString().trim())) {
            binding.password.requestFocus()
            binding.password.error = "Enter your father name"
        } else if (TextUtils.isEmpty(binding.address.text.toString().trim())) {
            binding.address.requestFocus()
            binding.address.error = "Enter your Father mobile no"
        } else if (TextUtils.isEmpty(binding.cnicNo.text.toString().trim())) {
            binding.cnicNo.requestFocus()
            binding.cnicNo.error = "Enter your CNIC no"
        } else if (imageUri == null) {
            Toast.makeText(requireContext(), "please click your CNIC picture", Toast.LENGTH_SHORT)
                .show()
        } else {
            continueRegistration(
                viewModel.formFirst.userEmail,
                binding.password.text.toString().trim()
            )
            binding.progressBar.visibility = View.VISIBLE
            binding.next.visibility = View.INVISIBLE

        }
    }


    private fun continueRegistration(email: String?, password: String) {

        mAuth.createUserWithEmailAndPassword(email!!, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    val user = task.result.user
                    if (user != null) {
                        Log.d("userInfo", "uploadUserData: $user")

                        uploadUserData(user)
                    } else {
                        binding.next.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
//                        binding.progressBar.visibility=View.GONE
//                        binding.signUp.visibility=View.VISIBLE
                        Toast.makeText(requireContext(), "No user found", Toast.LENGTH_SHORT).show()
                    }
                } else {
//                    binding.progressBar.visibility=View.GONE
//                    binding.signUp.visibility=View.VISIBLE
                    Toast.makeText(
                        requireContext(),
                        "Authentication failed. ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.progressBar.visibility = View.GONE
                    binding.next.visibility = View.VISIBLE

                }

            }
    }

    private fun uploadUserData(user: FirebaseUser) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val userData = FullRegistration(
            viewModel.formFirst.name,
            viewModel.formFirst.lName,
            viewModel.formFirst.mobileNo,
            viewModel.formFirst.faherName,
            viewModel.formFirst.faherNo,
            viewModel.formFirst.userEmail,
            education,
            viewModel.formFirst.gender,
            viewModel.formFirst.accountType,
            shiftTime,
            binding.password.text.toString(),
            binding.address.text.toString(),
            binding.cnicNo.text.toString(),
            currentDate,
            frontPage,
            backPage,
            user.uid,
            "",

        )
        FirebaseDatabase.getInstance().getReference("CSR")
            .child(user.uid)
            .setValue(userData)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("userInfo", "uploadUserData: $userData")
                    Common.CURRENT_USERS = userData
                    Toast.makeText(requireContext(), "upload data", Toast.LENGTH_SHORT).show()
                    navigateTo(viewModel.formFirst.accountType!!)
                } else {
                    binding.next.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "fail data", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun navigateTo(s: String) {
        if (s == "Director Sale") {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        } else {
            startActivity(Intent(requireContext(), CsrFloorManager::class.java))
        }

    }


}