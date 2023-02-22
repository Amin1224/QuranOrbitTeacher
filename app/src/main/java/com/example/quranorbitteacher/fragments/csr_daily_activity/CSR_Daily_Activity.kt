package com.example.quranorbitteacher.fragments.csr_daily_activity

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.activities.PendingTrial
import com.example.quranorbitteacher.databinding.FragmentCSRDailyBinding

class CSR_Daily_Activity : Fragment() {

    companion object {
        fun newInstance() = CSR_Daily_Activity()
    }

    private lateinit var viewModel: CSRDailyViewModel
    private lateinit var binding: FragmentCSRDailyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCSRDailyBinding.inflate(layoutInflater, container, false)


        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CSRDailyViewModel::class.java)

        binding.pendingTrial.setOnClickListener {
            startActivity(Intent(requireContext(),PendingTrial::class.java))
        }
        binding.pendingScheduleCall.setOnClickListener {
            findNavController().navigate(R.id.action_CSR_Daily_Activity_to_pending_Schedule_Call)
        }
        binding.csrReport.setOnClickListener {
            findNavController().navigate(R.id.action_CSR_Daily_Activity_to_CSR)
        }
        binding.csrProfile.setOnClickListener {
          moveToProfile()
        }
        binding.circleImage.setOnClickListener {
            moveToProfile()
        }
    }

    private fun moveToProfile() {
        findNavController().navigate(R.id.action_CSR_Daily_Activity_to_profile2)
    }

}