package com.example.quranorbitteacher.shared_pref

import android.app.Application

val prefs:SharedPref by lazy {
    App.prefs!!
}

class App:Application() {
    companion object{

        var prefs:SharedPref?=null
    }

    override fun onCreate() {
        prefs=SharedPref(applicationContext)
        super.onCreate()
    }

}