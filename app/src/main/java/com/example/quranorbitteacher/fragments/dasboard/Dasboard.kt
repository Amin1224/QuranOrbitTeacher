package com.example.quranorbitteacher.fragments.dasboard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.FragmentDasboardBinding

class Dasboard : Fragment() {


    private lateinit var viewModel: DasboardViewModel
    private lateinit var binding:FragmentDasboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentDasboardBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DasboardViewModel::class.java)
        binding.jumpToProfile.setOnClickListener {
            findNavController().navigate(R.id.action_dasboard_to_profile)
        }
    }

}