package com.example.quranorbitteacher.fragments.floor_manager.dashboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.FragmentFloorManagerDashboardBinding

class FloorManagerDashboard : Fragment() {

    private lateinit var binding:FragmentFloorManagerDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentFloorManagerDashboardBinding.inflate(layoutInflater, container, false)


   return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Use the ViewModel
        binding.csrReport.setOnClickListener {
         findNavController().navigate(R.id.action_floorManagerDashboard_to_csrReportInfo)
        }
        binding.receivedCall.setOnClickListener {
            findNavController().navigate(R.id.action_floorManagerDashboard_to_recievedCall)
        }
        binding.circleImage.setOnClickListener {
            moveToProfile()
        }
        binding.csrProfile.setOnClickListener {
            moveToProfile()
        }
    }

    private fun moveToProfile() {
        findNavController().navigate(R.id.action_floorManagerDashboard_to_profile3)
    }

}