package com.example.quranorbitteacher.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.ActivityPendingTrialBinding

class PendingTrial : AppCompatActivity() {
    private val binding by lazy {
        ActivityPendingTrialBinding.inflate(layoutInflater)
    }
    private val controller by lazy {
        findNavController(R.id.nav_host_fragment)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary_color)


        binding.requestText.setTextColor(resources.getColor(R.color.white))
        binding.request.setBackgroundDrawable(resources.getDrawable(R.drawable.request_btn))



        binding.request.setOnClickListener{
           controller.navigate(R.id.pending_Trial2)
            binding.requestText.setTextColor(resources.getColor(R.color.white))
            binding.request.setBackgroundDrawable(resources.getDrawable(R.drawable.request_btn))
            binding.aprovedText.setTextColor(resources.getColor(R.color.black))
            binding.aproved.setBackgroundDrawable(resources.getDrawable(R.drawable.approved_white))

        }
        binding.aproved.setOnClickListener {
            controller.navigate(R.id.aprovedTrial)
            binding.requestText.setTextColor(resources.getColor(R.color.black))
            binding.request.setBackgroundDrawable(resources.getDrawable(R.drawable.request_white))
            binding.aprovedText.setTextColor(resources.getColor(R.color.white))
            binding.aproved.setBackgroundDrawable(resources.getDrawable(R.drawable.aproved_btn))

        }
        binding.backPress.setOnClickListener {
           startActivity(Intent(this@PendingTrial,MainActivity::class.java))
        }


    }
}


