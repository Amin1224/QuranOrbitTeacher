package com.example.quranorbitteacher.fragments.pending_trial

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quranorbitteacher.call_utils.CallInfo
import com.example.quranorbitteacher.model.StudentForm3

class PendingTrialViewModel : ViewModel() {
    val userList= MutableLiveData<MutableList<StudentForm3>>()
    val teacherAssign= MutableLiveData<MutableList<StudentForm3>>()


    val erroMessage= MutableLiveData<String>()

    fun trialInfo(){
        CallInfo.TrialInfo{schedule,assignTeacher,message ->
            erroMessage.value=message
            userList.value=schedule
            teacherAssign.value=assignTeacher
        }
    }


}