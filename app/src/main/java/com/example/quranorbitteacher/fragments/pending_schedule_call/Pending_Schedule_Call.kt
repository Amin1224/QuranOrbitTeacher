package com.example.quranorbitteacher.fragments.pending_schedule_call

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
import com.example.quranorbitteacher.adapter.TrialHistory
import com.example.quranorbitteacher.databinding.FragmentPendingScheduleCallBinding

class Pending_Schedule_Call : Fragment() {

    companion object {
        fun newInstance() = Pending_Schedule_Call()
    }

    private lateinit var viewModel: PendingScheduleCallViewModel
    lateinit var binding: FragmentPendingScheduleCallBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingScheduleCallBinding.inflate(layoutInflater, container, false)
        val layoutManger: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.layoutManager = layoutManger

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(requireActivity()).get(PendingScheduleCallViewModel::class.java)
        viewModel.callHistory()
        binding.backPress.setOnClickListener {
            requireActivity().onBackPressed()
        }
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