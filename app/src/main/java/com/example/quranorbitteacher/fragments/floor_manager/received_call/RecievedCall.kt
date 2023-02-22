package com.example.quranorbitteacher.fragments.floor_manager.received_call

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quranorbitteacher.adapter.SchuduleCall
import com.example.quranorbitteacher.databinding.FragmentRecievedCallBinding
import com.example.quranorbitteacher.fragments.pending_schedule_call.PendingScheduleCallViewModel

class RecievedCall : Fragment() {

    companion object {
        fun newInstance() = RecievedCall()
    }

    private lateinit var viewModel: PendingScheduleCallViewModel
    private lateinit var binding: FragmentRecievedCallBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecievedCallBinding.inflate(layoutInflater, container, false)
        val layoutManger: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.layoutManager = layoutManger
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(PendingScheduleCallViewModel::class.java)
        viewModel.callHistory()
        viewModel.userList.observe(viewLifecycleOwner) { users ->
            binding.progressBar.visibility = View.INVISIBLE
            if (users.isNotEmpty()) {
                val adapter = SchuduleCall(requireContext(), users) {

                }
//                { itemList->
                //  Dailog.trialDay(requireActivity(),itemList)
//                }
                binding.recycleView.adapter = adapter
                binding.noDataFound.visibility = View.INVISIBLE
            } else {
                binding.noDataFound.visibility = View.VISIBLE
            }
        }
        viewModel.erroMessage.observe(viewLifecycleOwner) { messege ->
            if (messege.isNotEmpty()) {
                binding.noDataFound.visibility = View.VISIBLE
                Toast.makeText(requireContext(), messege, Toast.LENGTH_SHORT).show()
            }
        }
    }

}