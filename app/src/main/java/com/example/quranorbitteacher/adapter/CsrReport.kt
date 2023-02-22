package com.example.quranorbitteacher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quranorbitteacher.databinding.CsrReportBinding
import com.example.quranorbitteacher.databinding.ReportReceivedCallBinding
import com.example.quranorbitteacher.model.ScheduleCall

class CsrReport (
    val context: Context,
    val callReport: MutableList<ScheduleCall>,
    val sure: (String) -> Unit
) : RecyclerView.Adapter<CsrReport.ViewHolder>() {
    class ViewHolder(val binding: CsrReportBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CsrReport.ViewHolder {
        val binding = CsrReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return callReport.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(callReport[position]) {
                binding.clientName.text = this.name
                binding.dateStatus.text = this.mobileNo
                binding.skypeId.text = this.skypeId
                binding.dateShow.text=this.dateNtime
                binding.coursName.text=this.courseName

            }
        }
    }

}