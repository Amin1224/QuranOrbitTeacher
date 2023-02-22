package com.example.quranorbitteacher.fragments.call_counter

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.quranorbitteacher.R
import com.example.quranorbitteacher.common.Common
import com.example.quranorbitteacher.csr_dailog.CsrDailog
import com.example.quranorbitteacher.databinding.FragmentCallCounterBinding
import com.example.quranorbitteacher.shared_pref.prefs
import java.text.SimpleDateFormat
import java.util.*

class CallCounter : Fragment() {

    private lateinit var viewModel: CallCounterViewModel
    private lateinit var binding: FragmentCallCounterBinding
    var counting: Int = 0
    private var currentNumber: Int = 0
    private var attentCall: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCallCounterBinding.inflate(layoutInflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CallCounterViewModel::class.java)
        setData()
        onClick()
        currentNumber = prefs.dialCalls
        binding.showCounter.text = "$currentNumber"

        attentCall = prefs.attendedCalls


    }

    private fun onClick() {

        binding.attendedCall.isEnabled = false
        binding.reportDaily.setOnClickListener {
            findNavController().navigate(R.id.action_call_Counter_to_csrReport)
        }

        binding.backPress.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.dailCall.setOnClickListener {
            // Handler() used for delayed time of counter
            counting++
            binding.attendedCall.isEnabled = true
            currentNumber++
            prefs.dialCalls = currentNumber

            binding.showCounter.text = "$currentNumber"

            if (counting == 3) {
                binding.dailCall.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
                binding.dailCall.setBackgroundColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.card_color
                    )
                )
                binding.dailCall.isEnabled = false
                counting = 0

            } else if (counting == 1) {
                callCounter()
            }

        }

        binding.attendedCall.setOnClickListener {

            attentCall++
            prefs.attendedCalls = attentCall
            CsrDailog.showDialogOne(requireContext()) {
                if (it == "TrialBooking") {
                    findNavController().navigate(R.id.action_call_Counter_to_form1)
                } else if (it == "ScheduleCall") {

                    findNavController().navigate(R.id.action_call_Counter_to_schedule_Call)
                }
            }
        }
    }

    private fun setData() {
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm")
        val currentDate = sdf.format(Date())
        binding.csrName.text = Common.CURRENT_USERS.name
        binding.csrLastName.text = Common.CURRENT_USERS.lName
        binding.currentTime.text = currentDate
        // binding.showCounter.text = singleCount.toString()

    }

    private fun callCounter() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.dailCall.setTextColor(resources.getColor(R.color.black))
            binding.dailCall.setBackgroundDrawable(resources.getDrawable(R.drawable.dail_call_white))
            binding.dailCall.isEnabled = true
        }, 30000) // 30k is the delayed time in milliseconds.

    }

}