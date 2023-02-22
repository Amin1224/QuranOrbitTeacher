package com.example.quranorbitteacher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quranorbitteacher.databinding.ReportReceivedCallBinding
import com.example.quranorbitteacher.model.ScheduleCall

class SchuduleCall (
    val context: Context,
    val trialHistory: MutableList<ScheduleCall>,
    val sure:(String) ->Unit
) : RecyclerView.Adapter<SchuduleCall.ViewHolder>() {
    class ViewHolder(val binding: ReportReceivedCallBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding= ReportReceivedCallBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(trialHistory[position]) {
                binding.clientName.text = this.name
                binding.dateStatus.text = this.mobileNo
                binding.skypeId.text = this.skypeId
                binding.dateShow.text=this.dateNtime
                binding.coursName.text=this.courseName
                binding.seeDetail.setOnClickListener {
                    //  sure.invoke(this.uid!!)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return trialHistory.size
    }
}