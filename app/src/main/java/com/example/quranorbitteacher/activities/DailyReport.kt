package com.example.quranorbitteacher.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.quranorbitteacher.R

class DailyReport : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_report)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_color)

    }
}