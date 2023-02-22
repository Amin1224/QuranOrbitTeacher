package com.example.quranorbitteacher.fragments.csr

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.FragmentCSRBinding

class CSR : Fragment() {

    companion object {
        fun newInstance() = CSR()
    }

    private lateinit var viewModel: CSRViewModel
    private lateinit var binding: FragmentCSRBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCSRBinding.inflate(layoutInflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CSRViewModel::class.java)

        binding.csrReport.setOnClickListener {
            findNavController().navigate(R.id.action_CSR_to_form1)
        }

        binding.dailyActivity.setOnClickListener {
            findNavController().navigate(R.id.action_CSR_to_call_Counter)
        }
        binding.scheduleCall.setOnClickListener {
          findNavController().navigate(R.id.action_CSR_to_schedule_Call)
        }
        binding.circleImage.setOnClickListener {
            moveToProfile()
        }
        binding.csrProfile.setOnClickListener {
            moveToProfile()
        }
    }
    private fun moveToProfile() {
        findNavController().navigate(R.id.action_CSR_to_profile2)
    }

}