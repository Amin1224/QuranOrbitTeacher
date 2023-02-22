package com.example.quranorbitteacher.fragments.pending_trial

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quranorbitteacher.adapter.TrialHistory
import com.example.quranorbitteacher.csr_dailog.CsrDailog
import com.example.quranorbitteacher.databinding.FragmentPendingTrialBinding

class Pending_Trial : Fragment() {

    private lateinit var viewModel: PendingTrialViewModel
    lateinit var binding: FragmentPendingTrialBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPendingTrialBinding.inflate(layoutInflater, container, false)
        val layoutManger: RecyclerView.LayoutManager = LinearLayoutManager(requireContext())
        binding.recycleView.layoutManager = layoutManger
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(PendingTrialViewModel::class.java)
        viewModel.trialInfo()
        viewModel.userList.observe(viewLifecycleOwner) { users ->
            binding.progressBar.visibility = View.INVISIBLE
            if (users.isNotEmpty()) {
                val adapter = TrialHistory(requireContext(), users) { itemList ->
                    CsrDailog.trialDays(requireContext()) { a ->
                        if (a == null) {
                            Toast.makeText(
                                requireContext(),
                                "Select trial days",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            CsrDailog.idApproved(requireContext())
                        }
                    }
                }
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