package com.example.quranorbitteacher.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.databinding.ActivitySplashScreenBinding
import com.example.quranorbitteacher.model.FullRegistration
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
        FirebaseDatabase.getInstance().getReference("CSR")
            .child(uid)
            .get().addOnFailureListener {
                navigateTo("-1")
            }
            .addOnSuccessListener {
                if (it.exists()) {
                    for (itemSnap in it.children) {
                        val currentUser = it.getValue(FullRegistration::class.java)
                        if (currentUser != null) {
                            Common.CURRENT_USERS = currentUser
                            navigateTo(Common.CURRENT_USERS.accountType!!)
                        } else {
                            navigateTo("-1")
                        }
                    }
                }else{
                    navigateTo("-1")
                }
            }
    }

    private fun navigateTo(currentUser: String) {
        if (currentUser == "Director Sale") {
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        } else if (currentUser == "Floor Manager" ) {
            startActivity(Intent(this@SplashScreen, CsrFloorManager::class.java))
        } else {
            startActivity(Intent(this@SplashScreen, Registration::class.java))
        }
        finish()
    }

}