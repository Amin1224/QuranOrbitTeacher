package com.example.quranorbitteacher

import android.app.Application
import com.google.firebase.FirebaseApp

class QuranOrbitTeacher : Application() {


    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}