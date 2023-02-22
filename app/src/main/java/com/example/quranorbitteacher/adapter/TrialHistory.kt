package com.example.quranorbitteacher.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.quranorbitteacher.databinding.RequestCallBinding
import com.example.quranorbitteacher.model.StudentForm3

class TrialHistory(
    val context:Context,
    val trialHistory: MutableList<StudentForm3>,
    val sure:(String) ->Unit
) : RecyclerView.Adapter<TrialHistory.ViewHolder>() {
    class ViewHolder(val binding:RequestCallBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=RequestCallBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(trialHistory[position]) {
                binding.studentName.text=this.name
                binding.dateStatus.text = this.currentDate
                binding.requestTime.text = this.time
                binding.coursName.text=this.course

                if (this.teacherName == null){
                    binding.teacherStatus.setText("Not Assign")
                }else{
                    binding.teacherStatus.setText("Assign")
                }
                binding.assignTeacher.setOnClickListener {
                   sure.invoke(this.uid!!)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return trialHistory.size
    }
}