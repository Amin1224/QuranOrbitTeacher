package com.example.quranorbitteacher.fragments.sign_in

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
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
import com.example.quranorbitteacher.databinding.FragmentSignInBinding
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.model.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class Sign_In : Fragment() {

    companion object {
        fun newInstance() = Sign_In()
    }

    private lateinit var viewModel: SignInViewModel
    private lateinit var binding: FragmentSignInBinding

    private val mAuth by lazy {
        Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        onClick()
    }

    private fun onClick() {
        binding.login.setOnClickListener {
            checkInput()
        }
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_sign_In_to_sign_Up2)
        }

    }

    private fun checkInput() {
        if (!Helpher.isEmailValid(binding.email.text.toString().trim())) {
            binding.email.error = "Enter valid email"
            binding.email.requestFocus()
        } else if (TextUtils.isEmpty(binding.password.text.toString().trim())) {
            binding.password.error = "Enter valid password"
            binding.password.requestFocus()
        } else {
            binding.login.visibility=View.INVISIBLE
            binding.progressBar.visibility=View.VISIBLE
            signIn(binding.email.text.toString().trim(), binding.password.text.toString().trim())
        }
    }

    private fun signIn(email: String, passWord: String) {
        mAuth.signInWithEmailAndPassword(email, passWord)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    task.getResult().user?.let { updateUI(it.uid) }
                } else {
                    Toast.makeText(
                        requireActivity(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.login.visibility=View.VISIBLE
                    binding.progressBar.visibility=View.GONE
                }
            }
    }

    private fun updateUI(user: String) {
        FirebaseDatabase.getInstance().getReference("TEACHER")
            .child(user)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    if (snapshot.exists()) {
                        for (itemSnap in snapshot.children) {
                        val currentUser = itemSnap.getValue(User::class.java)
                        if (currentUser != null) {
                            Common.CURRENT_USER = currentUser
                            navigateTo("1")
                        } else {
                            Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT).show()
                            binding.login.visibility=View.VISIBLE
                            binding.progressBar.visibility=View.GONE
                        }
                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            "No Account found against these details",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.login.visibility=View.VISIBLE
                        binding.progressBar.visibility=View.GONE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    binding.login.visibility=View.VISIBLE
                    binding.progressBar.visibility=View.GONE
                    Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun navigateTo(user: String) {
        if (user == "1" && Common.CURRENT_USER.notActive==true ) {
            startActivity(Intent(requireContext(),Home::class.java))
        }else{
            startActivity(Intent(requireContext(),AccontNotAproved::class.java))
        }
        requireActivity().finish()

    }
}

