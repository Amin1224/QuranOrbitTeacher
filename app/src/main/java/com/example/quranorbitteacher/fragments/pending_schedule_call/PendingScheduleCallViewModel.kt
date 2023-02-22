package com.example.quranorbitteacher.fragments.pending_schedule_call

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quranorbitteacher.call_utils.CallInfo
import com.example.quranorbitteacher.model.ScheduleCall
import com.example.quranorbitteacher.model.StudentForm3

class PendingScheduleCallViewModel : ViewModel() {
    val userList= MutableLiveData<MutableList<ScheduleCall>>()

    val erroMessage= MutableLiveData<String>()

    fun callHistory(){
        CallInfo.callInfo (){call,message ->
            erroMessage.value=message
            userList.value=call
        }
    }
}