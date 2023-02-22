package com.example.quranorbitteacher.call_utils

import android.util.Log
import android.widget.Toast
import com.example.quranorbitteacher.model.ScheduleCall
import com.example.quranorbitteacher.model.StudentForm3
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CallInfo {
    companion object {
        fun callInfo(
            event: (MutableList<ScheduleCall>, String) -> Unit
        ) {
            val tempList = mutableListOf<ScheduleCall>()
            FirebaseDatabase.getInstance().getReference("CallerSchedule")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (itemSnap in snapshot.children) {
                            val scheduleCall = itemSnap.getValue(ScheduleCall::class.java)
                            if (scheduleCall != null) {
                                tempList.add(scheduleCall)
                            }
                        }
                        event.invoke(tempList, "")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        event.invoke(tempList, error.message)
                    }

                })
        }

        fun TrialInfo(
            event: (MutableList<StudentForm3>, MutableList<StudentForm3>, String) -> Unit
        ) {
            val tempList = mutableListOf<StudentForm3>()
            val tempListAssignTeacher = mutableListOf<StudentForm3>()
            FirebaseDatabase.getInstance().getReference("STUDENT_REGISTER")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (itemSnap in snapshot.children) {
                            val trialDetails = itemSnap.getValue(StudentForm3::class.java)
                            if (trialDetails != null) {
                                if (trialDetails.teacherUID == null) {
                                    Log.i("trialChecking", "trialChecking: $trialDetails")
                                    tempList.add(trialDetails)
                                } else {
                                    tempListAssignTeacher.add(trialDetails)
                                }

                            }
                        }
                        event.invoke(tempList, tempListAssignTeacher, "")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        event.invoke(tempList, tempListAssignTeacher, error.message)
                    }

                })
        }

        fun callReportInfo(
            event: (MutableList<ScheduleCall>, String) -> Unit
        ) {
            val tempList = mutableListOf<ScheduleCall>()
            FirebaseDatabase.getInstance().getReference("CallsReport")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (itemSnap in snapshot.children) {
                            val scheduleCall = itemSnap.getValue(ScheduleCall::class.java)
                            if (scheduleCall != null) {
                                tempList.add(scheduleCall)
                            }
                        }
                        event.invoke(tempList, "")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        event.invoke(tempList, error.message)
                    }

                })
        }

    }
}