package com.example.quranorbitteacher.fragments.form2

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quranorbitteacher.R

class Form2 : Fragment() {

    companion object {
        fun newInstance() = Form2()
    }

    private lateinit var viewModel: Form2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_form2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(Form2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}