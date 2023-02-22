package com.example.quranorbitteacher.fragments.sign_in

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.activities.CsrFloorManager
import com.example.quranorbitteacher.activities.MainActivity
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.databinding.FragmentSignInBinding
import com.example.quranorbitteacher.helpher.Helpher
import com.example.quranorbitteacher.model.FullRegistration
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

        binding.email.isSingleLine = true
        binding.password.isSingleLine = true
        binding.signIn.setOnClickListener {
            checkInput()
        }
        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_sign_In3_to_registration2)
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
            binding.signIn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.VISIBLE
            signIn(binding.email.text.toString().trim(), binding.password.text.toString().trim())
        }
    }

    private fun signIn(email: String, passWord: String) {
        mAuth.signInWithEmailAndPassword(email, passWord)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    task.getResult().user?.let {
                        updateUI(it.uid)
                        Log.d("itUID", "signIn: jjjjjjjjjjj")
                        Toast.makeText(requireContext(), "succefully", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(
                        requireActivity(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.signIn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                }
            }
    }

    private fun updateUI(uid: String) {
        FirebaseDatabase.getInstance().getReference("CSR")
            .child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.i("kTAG", "itemSnap: ${snapshot}")

                    Toast.makeText(requireContext(), "store data", Toast.LENGTH_SHORT).show()
                    if (snapshot.exists()) {
//                        for (itemSnap in snapshot.children) {
                            Log.i("kTAG", "current: $snapshot")

                            val currentUser = snapshot.getValue(FullRegistration::class.java)
                            if (currentUser != null) {
                                Toast.makeText(requireContext(), "currentUser", Toast.LENGTH_SHORT)
                                    .show()

                                Log.i("kTAG", "currentuser: $currentUser")
                                Common.CURRENT_USERS = currentUser
                                navigateTo(Common.CURRENT_USERS.accountType!!)
                            }
                            else {
                                Toast.makeText(requireContext(), "failed", Toast.LENGTH_SHORT)
                                    .show()
                                binding.signIn.visibility = View.VISIBLE
                                binding.progressBar.visibility = View.GONE
                            }
//                        }

                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            "No Account found against these details",
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.signIn.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    binding.signIn.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), "$error", Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun navigateTo(user: String) {
        if (user == "Director Sale"  ) {
            startActivity(Intent(requireContext(), MainActivity::class.java))
        }else {
            startActivity(Intent(requireContext(),CsrFloorManager::class.java))
        }
        requireActivity().finish()


    }
}

