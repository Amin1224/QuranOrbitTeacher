package com.example.quranorbitteacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.quranorbitteacher.R

class AccontNotAproved : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accont_not_aproved)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_color)

    }
}