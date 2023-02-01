package com.example.quranorbitteacher.fragments.sign_up

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.activities.AccontNotAproved
import com.example.quranorbitteacher.activities.Home
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.databinding.FragmentSignUpBinding
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class Sign_Up : Fragment() {

    companion object {
        fun newInstance() = Sign_Up()
    }

    private lateinit var viewModel: SignUpViewModel
    private lateinit var binding :FragmentSignUpBinding
    private var accountAprove :Boolean = false


    private val mAuth by lazy {
        Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentSignUpBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        onClick()
    }
    private fun onClick() {
        binding.alreadyAccount.setOnClickListener {
            findNavController().navigate(R.id.action_sign_Up2_to_sign_In)
        }

        binding.signUp.setOnClickListener {
            checkInput()
        }
    }

    private fun checkInput() {

        if (TextUtils.isEmpty(binding.userName.text.toString().trim())) {
            binding.userName.error = "Enter your name"
            binding.userName.requestFocus()
        } else if (!Helpher.isEmailValid(binding.email.text.toString().trim())) {
            binding.email.error = "Enter your correct email"
            binding.email.requestFocus()
        } else if (TextUtils.isEmpty(binding.password.text.toString().trim())) {
            binding.password.error = "Enter your correct email"
            binding.password.requestFocus()
        } else if (TextUtils.isEmpty(binding.phoneNumber.text.toString().trim())) {
            binding.phoneNumber.error = "Enter your correct phone No"
            binding.phoneNumber.requestFocus()
        } else {
            binding.progressBar.visibility=View.VISIBLE
            binding.signUp.visibility=View.INVISIBLE
            continueRegistration(binding.email.text.toString().trim(),binding.password.text.toString().trim())
        }
    }

    private fun continueRegistration(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(requireActivity()){task ->
                if (task.isSuccessful){
                    val user =task.result.user
                    if (user !=null){
                        Log.d("userInfo", "uploadUserData: $user")

                        uploadUserData(user)
                    }else{
                        binding.progressBar.visibility=View.GONE
                        binding.signUp.visibility=View.VISIBLE
                        Toast.makeText(requireContext(), "No user found", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    binding.progressBar.visibility=View.GONE
                    binding.signUp.visibility=View.VISIBLE
                    Toast.makeText(requireContext(), "Authentication failed. ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }

            }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun uploadUserData(user: FirebaseUser) {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val userModel= User(
            binding.userName.text.toString().trim(),
            binding.email.text.toString().trim(),
            binding.password.text.toString().trim(),
            accountAprove,
            binding.phoneNumber.text.toString().trim(),
            currentDate,
            user.uid,
            ""
        )
        FirebaseDatabase.getInstance().getReference("TEACHER")
            .child(user.uid)
            .setValue(userModel)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    Log.d("userInfo", "uploadUserData: $userModel")
                    Common.CURRENT_USER=userModel
                    navigateTo("1")
                }else{
                    binding.progressBar.visibility=View.GONE
                    binding.signUp.visibility=View.VISIBLE
                    Toast.makeText(requireContext(), it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                binding.progressBar.visibility=View.GONE
                binding.signUp.visibility=View.VISIBLE
                Toast.makeText(requireContext(), "your signup Fail", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateTo(jump: String) {
        if (jump == "1" && accountAprove){
            startActivity(Intent(requireContext(), Home::class.java))
        }else{
          startActivity(Intent(requireActivity(),AccontNotAproved::class.java))
        }
        requireActivity().finish()
    }


}