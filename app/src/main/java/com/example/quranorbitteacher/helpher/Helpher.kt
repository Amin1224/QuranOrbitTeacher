package com.example.quranorbitteacher.helpher

import android.util.Patterns

class Helpher {
    companion object {
        fun isEmailValid(trim: String?): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(trim).matches()
        }
    }
}