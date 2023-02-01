package com.example.quranorbitteacher.fragments.form1

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.databinding.FragmentForm1Binding

class Form1 : Fragment() {

    companion object {
        fun newInstance() = Form1()
    }

    private lateinit var viewModel: Form1ViewModel
    private lateinit var binding:FragmentForm1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding=FragmentForm1Binding.inflate(layoutInflater, container, false)

    return binding.root}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(Form1ViewModel::class.java)

    }

}