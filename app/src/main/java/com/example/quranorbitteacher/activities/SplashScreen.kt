package com.example.quranorbitteacher.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.databinding.ActivitySplashScreenBinding
import com.example.quranorbitteacher.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SplashScreen : AppCompatActivity() {
    private val binding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    private val auth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
         if (auth.currentUser != null) {
            fetchDataFirebase(auth.currentUser!!.uid)
        } else {
            navigateTo("-1")
        }
    }

    private fun fetchDataFirebase(uid: String) {
        FirebaseDatabase.getInstance().getReference("TEACHER")
            .child(uid)
            .get().addOnFailureListener {
                navigateTo("-1")
            }
            .addOnSuccessListener {
                if (it.exists()) {
                    Log.d("TAG", "fetchDataFirebase: $it")
                    for (itemSnap in it.children) {
                          val currentUser = it.getValue(User::class.java)
                        if (currentUser != null) {
                               Common.CURRENT_USER = currentUser
                            navigateTo("1")
                        } else {
                            navigateTo("-1")
                        }
                    }
                }
            }
    }

    fun navigateTo(currentUser: String) {
        if (currentUser == "-1") {
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        } else if (currentUser == "1" && Common.CURRENT_USER.notActive == true) {
            startActivity(Intent(this@SplashScreen, Home::class.java))
        } else {
            startActivity(Intent(this@SplashScreen, AccontNotAproved::class.java))
        }
        finish()
    }

}