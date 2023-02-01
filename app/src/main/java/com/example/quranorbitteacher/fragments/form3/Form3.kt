package com.example.quranorbitteacher.fragments.form3

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quranorbitteacher.R

class Form3 : Fragment() {

    companion object {
        fun newInstance() = Form3()
    }

    private lateinit var viewModel: Form3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form3, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Form3ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}